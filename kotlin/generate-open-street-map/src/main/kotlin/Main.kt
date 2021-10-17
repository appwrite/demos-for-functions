import io.appwrite.Client
import io.appwrite.services.Storage
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import model.AppwriteFile
import model.Location
import util.Env
import java.io.File
import kotlin.math.*

private const val LOCATION_KEY = "APPWRITE_FUNCTION_DATA"
private const val ZOOM_LEVEL_KEY = "ZOOM_LEVEL"
private const val APPWRITE_ENDPOINT_KEY = "APPWRITE_ENDPOINT"
private const val APPWRITE_PROJECT_ID_KEY = "APPWRITE_FUNCTION_PROJECT_ID"
private const val APPWRITE_SECRETKEY_KEY = "APPWRITE_API_KEY"

suspend fun main() {
    val appwriteClient = Client()
        .setEndpoint(Env.readString(APPWRITE_ENDPOINT_KEY))
        .setProject(Env.readString(APPWRITE_PROJECT_ID_KEY))
        .setKey(Env.readString(APPWRITE_SECRETKEY_KEY))

    val appwriteStorage = Storage(appwriteClient)

    val jsonParser = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }

    val location: Location = Env.read(LOCATION_KEY) { jsonParser.decodeFromString(it) }
    val zoom: Int = Env.read(ZOOM_LEVEL_KEY, 15) { it.toInt() }

    val (x, y) = getXYTile(location, zoom)

    val locationTile = appwriteStorage.createFromUrl(
        filename = "osm_tile_${location.latitude}_${location.longitude}_z$zoom.png",
        url = "http://a.tile.openstreetmap.org/$zoom/$x/$y.png",
        jsonParser = jsonParser
    )

    println(locationTile.id)
}

// Adapted from https://wiki.openstreetmap.org/wiki/Slippy_map_tilenames#Kotlin
fun getXYTile(location: Location, zoom: Int): Pair<Int, Int> {
    val latRad = Math.toRadians(location.latitude)
    var xtile = floor((location.longitude + 180) / 360 * (1 shl zoom)).toInt()
    var ytile = floor((1.0 - asinh(tan(latRad)) / PI) / 2 * (1 shl zoom)).toInt()

    if (xtile < 0) {
        xtile = 0
    }
    if (xtile >= (1 shl zoom)) {
        xtile = (1 shl zoom) - 1
    }
    if (ytile < 0) {
        ytile = 0
    }
    if (ytile >= (1 shl zoom)) {
        ytile = (1 shl zoom) - 1
    }

    return Pair(xtile, ytile)
}

suspend fun Storage.createFromUrl(filename: String, url: String, jsonParser: Json): AppwriteFile {
    val file = File(filename)

    HttpClient().use { httpClient ->
        val httpResponse: HttpResponse = httpClient.get(url)
        val responseBody: ByteArray = httpResponse.receive()
        file.writeBytes(responseBody)
    }

    val appwriteFile: AppwriteFile = createFile(file).body?.string()!!.run {
        jsonParser.decodeFromString(this)
    }

    file.deleteOnExit()
    return appwriteFile
}