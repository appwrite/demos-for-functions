import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import model.ImageResult
import model.ResultWrapper

const val BASE_URL = "https://api.unsplash.com"
const val CLIENT_ID_KEY = "UNSPLASH_ACCESS_KEY"
const val KEYWORD_KEY = "APPWRITE_FUNCTION_DATA"

suspend fun main() {
    val clientId = try {
        System.getenv(CLIENT_ID_KEY)!!
    } catch (e: NullPointerException) {
        println("[ERR] No client ID found")
        return
    }

    val keyword = System.getenv(KEYWORD_KEY) ?: null

    if (keyword == null || keyword.isEmpty()) {
        println("[WARN] Keyword not set, exiting")
        return
    }

    HttpClient() {
        install(JsonFeature) {
            serializer = KotlinxSerializer(json = kotlinx.serialization.json.Json {
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }.use { client ->
        println("[INFO] Keyword: $keyword")

        val response: ResultWrapper = client.get("$BASE_URL/search/photos") {
            parameter("client_id", clientId)
            parameter("query", keyword)
            parameter("page", 1)
            parameter("per_page", 1)
        }

        if (response.results.isEmpty()) {
            println("[INFO] No results found for `$keyword`")
            return
        }

        showResult(response.results.first())

    }
}

fun showResult(result: ImageResult) {
    println("imageAuthor: ${result.imageAuthor}")
    println("imageUrl: ${result.imageUrl}")
}