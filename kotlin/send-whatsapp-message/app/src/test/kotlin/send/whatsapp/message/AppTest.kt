package send.whatsapp.message


class AppTest {
    @org.junit.Test
    fun sendWhatsAppMessage() {
        val accountSsid = "YOUR_ACCOUNT_SID"
        val authToken = "YOUR_AUTH_TOKEN"
        val fromNumber = "SENDER_NUMBER"
        val wa = Whatsapp(accountSsid, authToken, fromNumber)
        wa.sendWhatsAppMessage("+6281392944603", "ERRRR do you do?")
        //assertEquals(false, msg.success)
    }
}
