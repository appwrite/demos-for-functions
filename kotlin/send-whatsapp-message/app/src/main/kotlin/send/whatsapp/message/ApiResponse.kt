package send.whatsapp.message

data class ApiResponse(
    val account_sid: String,
    val api_version: String,
    val body: String,
    val date_created: String,
    val date_sent: String,
    val date_updated: String,
    val direction: String,
    val error_code: Any,
    val error_message: Any,
    val from: String,
    val messaging_service_sid: String,
    val num_media: String,
    val num_segments: String,
    val price: Any,
    val price_unit: Any,
    val sid: String,
    val status: String,
    val to: String,
    val uri: String
)