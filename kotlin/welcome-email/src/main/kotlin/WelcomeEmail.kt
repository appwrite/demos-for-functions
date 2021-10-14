import com.mashape.unirest.http.Unirest
import com.mashape.unirest.http.exceptions.UnirestException
import org.json.JSONObject

var YOUR_DOMAIN_NAME: String? = null
var API_KEY: String? = null

@Throws(UnirestException::class)
fun main(args: Array<String>) {
    YOUR_DOMAIN_NAME = System.getenv("MAILGUN_DOMAIN")
    API_KEY = System.getenv("MAILGUN_API_KEY")
    val payload = System.getenv("APPWRITE_FUNCTION_EVENT_DATA")

    if (payload != null && payload.isNotEmpty()) {
        try {
            val json = JSONObject(payload)
            val name = json.getString("name")
            val email = json.getString("email")
            val response = sendSimpleMessage(name, email)
            println(response)
        } catch (e: Exception) {
            print("[ERROR] There was an error")
            println(e.message)
        }
    } else {
        println("[INFO] APPWRITE_FUNCTION_EVENT_DATA is empty")
    }
}

@Throws(UnirestException::class)
fun sendSimpleMessage(name: String?, email: String?): String {
    val message = String.format("Welcome %s!", name)
    val request = Unirest.post("https://api.mailgun.net/v3/" + YOUR_DOMAIN_NAME + "/messages")
        .basicAuth("api", API_KEY)
        .field("from", "Excited User <hello@appwrite.io>")
        .field("to", email)
        .field("subject", "hello")
        .field("text", message)
        .asString()
    return request.body
}