import io.appwrite.Client
import io.appwrite.services.Storage
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*
import io.ktor.utils.io.core.*
import kotlinx.serialization.json.Json
import models.TinypngOutput
import models.TinypngRequest
import models.TinypngResponse
import utils.Env
import java.util.*
import kotlin.io.use
import kotlin.text.toByteArray

private const val IMAGE_URL_KEY = "APPWRITE_FUNCTION_DATA"
private const val TINYPNG_API_KEY = "TINYPNG_API_KEY"
private const val APPWRITE_ENDPOINT_KEY = "APPWRITE_ENDPOINT"
private const val APPWRITE_PROJECT_ID_KEY = "APPWRITE_FUNCTION_PROJECT_ID"
private const val APPWRITE_SECRET_KEY = "APPWRITE_API_KEY"

private const val TINYPNG_ENDPOINT = "https://api.tinify.com/shrink"

suspend fun main() {
//    val appwriteClient = Client()
//        .setEndpoint(Env.readString(APPWRITE_ENDPOINT_KEY))
//        .setProject(Env.readString(APPWRITE_PROJECT_ID_KEY))
//        .setKey(Env.readString(APPWRITE_SECRET_KEY))
//
//    val appwriteStorage = Storage(appwriteClient)

    val jsonParser = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }

    println(compressImage(
        imageUrl = Env.readString(IMAGE_URL_KEY),
        endpoint = TINYPNG_ENDPOINT,
        apiKey = Env.readString(TINYPNG_API_KEY),
        jsonParser = jsonParser
    ))

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