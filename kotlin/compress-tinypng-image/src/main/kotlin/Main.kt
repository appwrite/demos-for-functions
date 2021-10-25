import io.appwrite.Client
import io.appwrite.services.Storage
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import models.AppwriteFile
import models.TinypngRequest
import models.TinypngResponse
import utils.Env
import java.io.File
import java.util.*
import kotlin.system.exitProcess

private const val IMAGE_URL_KEY = "APPWRITE_FUNCTION_DATA"
private const val TINYPNG_API_KEY = "TINYPNG_API_KEY"
private const val APPWRITE_ENDPOINT_KEY = "APPWRITE_ENDPOINT"
private const val APPWRITE_PROJECT_ID_KEY = "APPWRITE_FUNCTION_PROJECT_ID"
private const val APPWRITE_SECRET_KEY = "APPWRITE_API_KEY"

private const val TINYPNG_ENDPOINT = "https://api.tinify.com/shrink"

suspend fun main() {
    val appwriteClient = Client()
        .setEndpoint(Env.readString(APPWRITE_ENDPOINT_KEY))
        .setProject(Env.readString(APPWRITE_PROJECT_ID_KEY))
        .setKey(Env.readString(APPWRITE_SECRET_KEY))

    val appwriteStorage = Storage(appwriteClient)

    val jsonParser = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }

    val baseImageUrl = Env.readString(IMAGE_URL_KEY)

    val compressedImageUrl = compressImage(
        imageUrl = baseImageUrl,
        endpoint = TINYPNG_ENDPOINT,
        apiKey = Env.readString(TINYPNG_API_KEY),
        jsonParser = jsonParser
    )

    val compressedImage = appwriteStorage.createFromUrl(
        filename = "compressed-tinypng-edition",
        url = compressedImageUrl,
        jsonParser
    )

    println(compressedImage.id)
    exitProcess(0)
}

suspend fun compressImage(
    imageUrl: String,
    endpoint: String,
    apiKey: String,
    jsonParser: Json
): String = HttpClient {
    install(JsonFeature) {
        serializer = KotlinxSerializer(json = jsonParser)
    }
}.use { client ->
    val response: TinypngResponse = client.post(endpoint) {
        header(HttpHeaders.Authorization, authHeaderValueFor(apiKey))
        header(HttpHeaders.ContentType, "application/json")

        body = TinypngRequest(imageUrl)
    }

    return response.output.url
}

fun authHeaderValueFor(apiKey: String): String =
    "Basic ${Base64.getEncoder().encodeToString("api:$apiKey".toByteArray())}"

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