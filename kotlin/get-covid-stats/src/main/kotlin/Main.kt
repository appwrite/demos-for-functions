import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import model.CountryData
import model.ICovidStats
import model.Response

const val BASE_URL = "https://api.covid19api.com";
const val COUNTRY_CODE_KEY = "APPWRITE_FUNCTION_DATA"

suspend fun main() {
    val country = (System.getenv(COUNTRY_CODE_KEY) ?: null).also {
        if (it == null) {
            println("[INFO] No country set in ENV, showing global stats")
        }
    }

    execute(country)
}

suspend fun execute(country: String? = null) = HttpClient() {
    install(JsonFeature)
}.use { client ->
    println("[INFO] Country: $country")

    val response: Response = client.get("$BASE_URL/summary")

    val countryData: CountryData? = country?.let {
        response.countries.find {
            it.country.equals(country, ignoreCase = true) ||
                    it.countryCode.equals(country, ignoreCase = true) ||
                    it.slug.equals(country, ignoreCase = true)
        }.also {
            if (it == null) {
                println("[INFO] Country is invalid, showing global stats")
            }
        }
    }

    val stats: ICovidStats = countryData ?: response.global

    printStats(stats, countryData?.country ?: "The World")

}

fun printStats(stats: ICovidStats, statsFor: String) {
    val msg = """COVID-19 stats for ${statsFor}:
                |Confirmed cases today: ${stats.newConfirmed}
                |Recovered today: ${stats.newRecovered}
                |Deaths today: ${stats.newDeaths}""".trimMargin()

    println(msg)
}