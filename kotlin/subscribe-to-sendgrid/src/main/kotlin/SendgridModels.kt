data class Contact(
    val email: String
)

data class SendgridSubscriptionRequest(
    val contacts: List<Contact>
)