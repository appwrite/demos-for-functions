import io.ktor.client.*
import io.ktor.client.request.*
import model.Response
import kotlin.system.exitProcess

const val BASE_URL = "https://api.giphy.com/v1/gifs/search"
const val GIPHY_API_KEY = "GIPHY_API_KEY"
const val KEYWORD_KEY = "APPWRITE_FUNCTION_DATA"

suspend fun main() {
    val giphyApiKey = readStringFromEnvironment(GIPHY_API_KEY)
    val keyword = readStringFromEnvironment(KEYWORD_KEY)

    if (!isValidKeyword(keyword)) {
        System.err.println("[ERR] keyword: $keyword is not valid")
        exitProcess(-1)
    }

    val url = getGifUrlFor(
        keyword = keyword,
        apiKey = giphyApiKey
    )

    if (url == null) {
        println("Sorry, No GIF found for $keyword")
    } else {
        println(url)
    }

    println("Powered By GIPHY")

}

suspend fun getGifUrlFor(
    keyword: String,
    apiKey: String
): String? = HttpClient {
    setupKotlinxSerializer()
}.use { client ->

    val response: Response = client.get(BASE_URL) {
        parameter("api_key", apiKey)
        parameter("q", keyword)
        parameter("limit", 1)
    }

    response.gifs.firstOrNull()?.url
}
