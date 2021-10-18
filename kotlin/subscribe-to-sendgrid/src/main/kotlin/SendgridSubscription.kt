import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sendgrid.Method
import com.sendgrid.Request
import com.sendgrid.SendGrid
import java.io.IOException

fun main(args: Array<String>) {
    val sendgrid = SendGrid(System.getenv("SENDGRID_API_KEY"))
    val request = Request()

    val funcData = Gson().fromJson<Map<String, String>>(
        System.getenv("APPWRITE_FUNCTION_DATA"),
        object : TypeToken<Map<String, String>>(){}.type
    )

    println(funcData)

    val email = funcData["email"] ?: throw Exception("Invalid arguments")
    val sendgridSubscriptionRequest = SendgridSubscriptionRequest(
        listOf(
            Contact(email = email)
        )
    )

    println(email)
    try {
        request.method = Method.PUT
        request.endpoint = "marketing/contacts"
        request.body = Gson().toJson(sendgridSubscriptionRequest)

        val response = sendgrid.api(request)

        println(response)
        if (response.statusCode >= 200) {
            println("Successfully subscribed $email to Sendgrid")
        }
    } catch (ex: IOException) {
        throw ex
    }
}