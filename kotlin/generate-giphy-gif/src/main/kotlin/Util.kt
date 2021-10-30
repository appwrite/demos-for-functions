import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlinx.serialization.json.Json
import kotlin.system.exitProcess

fun readStringFromEnvironment(key: String): String = try {
    System.getenv(key)!!
} catch (e: NullPointerException) {
    System.err.println("[ERR] `$key` not found in environment variables")
    exitProcess(-1)
}

fun isValidKeyword(keyword: String) = keyword.isNotEmpty()

fun <T: HttpClientEngineConfig> HttpClientConfig<T>.setupKotlinxSerializer()  {
    install(JsonFeature) {
        serializer = KotlinxSerializer(json = kotlinx.serialization.json.Json {
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
}