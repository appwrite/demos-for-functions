package model

import kotlinx.serialization.Serializable
import java.math.BigInteger

@Serializable
data class OutboundMessage(
    private val phoneNumber: String,
    val text: String
) {
    fun getPhoneNumberAsBigInteger(): BigInteger = BigInteger(phoneNumber)
}
