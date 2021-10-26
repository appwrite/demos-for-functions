package io.appwrite.send.message.bird.whatsapp.msg

import com.messagebird.MessageBirdClient
import com.messagebird.MessageBirdServiceImpl
import com.messagebird.exceptions.MessageBirdException
import com.messagebird.objects.conversations.ConversationContent
import com.messagebird.objects.conversations.ConversationContentType
import com.messagebird.objects.conversations.ConversationFallbackOption
import com.messagebird.objects.conversations.ConversationSendRequest
import io.appwrite.send.message.bird.whatsapp.msg.model.FunctionData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.system.exitProcess

fun main() {
    try {
        Application.run()
    } catch (exception: Exception) {
        exception.printStackTrace()
        exitProcess(1)
    }
    exitProcess(0)
}

object Application {
    private const val ENV_APPWRITE_FUNCTION_DATA = "APPWRITE_FUNCTION_DATA"
    private const val ENV_MESSAGEBIRD_KEY = "MESSAGEBIRD_ACCESS_KEY"
    private const val ENV_WHATSAPP_CHANNEL_ID = "WHATSAPP_CHANNEL_ID"
    private const val ENV_REPORT_URL = "REPORT_URL"
    private const val ENV_FALLBACK_CHANNEL_ID = "FALLBACK_CHANNEL_ID"
    private const val ENV_FALLBACK_AFTER = "FALLBACK_WINDOW"

    private val jsonParser = Json { isLenient = true }

    fun run() {
        val messageBirdClient = MessageBirdClient(MessageBirdServiceImpl(getMandatoryEnv(ENV_MESSAGEBIRD_KEY)))

        val functionData = System.getenv(ENV_APPWRITE_FUNCTION_DATA)
            ?.let { jsonParser.decodeFromString<FunctionData>(it) }
            ?: throw IllegalArgumentException("Function Data is required")
        println(functionData)

        val fallbackOption = System.getenv(ENV_FALLBACK_CHANNEL_ID)
            ?.let { ConversationFallbackOption(it, System.getenv(ENV_FALLBACK_AFTER)) }
        val request = ConversationSendRequest().apply {
            to = functionData.phoneNumber
            type = ConversationContentType.TEXT
            content = ConversationContent().apply { text = functionData.text }
            from = getMandatoryEnv(ENV_WHATSAPP_CHANNEL_ID)
            reportUrl = System.getenv(ENV_REPORT_URL)
            fallback = fallbackOption
            source = functionData.source
            tag = functionData.tag
        }
        val response = try {
            messageBirdClient.sendMessage(request)
        } catch (exception: MessageBirdException) {
            System.err.println("${exception.message}: ${exception.errors}")
            exitProcess(1)
        }
        println(response)
    }

    private fun getMandatoryEnv(env: String): String {
        return System.getenv(env)
            ?: throw RuntimeException("Environment Variable not configured: $env")
    }
}
