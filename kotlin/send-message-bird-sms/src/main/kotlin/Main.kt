import com.messagebird.MessageBirdClient
import com.messagebird.MessageBirdServiceImpl
import com.messagebird.exceptions.UnauthorizedException
import com.messagebird.objects.MessageResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import model.OutboundMessage
import util.Env

const val MESSAGEBIRD_APIKEY_KEY = "MESSAGEBIRD_API_KEY"
const val OUTBOUND_MESSAGE_KEY = "APPWRITE_FUNCTION_DATA"
const val ORIGINATOR_PHONE_NUMBER_KEY = "ORIGINATOR_PHONE_NUMBER"

fun main() {
    val jsonParser = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }

    val messagebirdApiKey = Env.readString(MESSAGEBIRD_APIKEY_KEY)
    val originatorPhoneNumber = Env.readString(ORIGINATOR_PHONE_NUMBER_KEY)
    val message: OutboundMessage = Env.read(OUTBOUND_MESSAGE_KEY) {
        jsonParser.decodeFromString(it)
    }

    val messageBirdClient = MessageBirdClient(
        MessageBirdServiceImpl(messagebirdApiKey)
    )

    try {
        val response: MessageResponse = messageBirdClient.sendMessage(
            originatorPhoneNumber,
            message.text,
            listOf(message.getPhoneNumberAsBigInteger())
        )!!

        println(response.recipients.items.first().status)
    } catch (e: UnauthorizedException) {
        System.err.println("Messagebird API Key is invalid")
        throw(e)
    } catch (e: Exception) {
        System.err.println("Unexpected Error Occurred")
        throw(e)
    }

}