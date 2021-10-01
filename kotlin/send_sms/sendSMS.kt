import com.twilio.Twilio
import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber

fun sendSMS(args:Array<String>)
{
    //INITIALISE TWILIO REST CLIENT
    Twilio.init(java.lang.System.getenv(name:"TWILIO_ACCOUNT_STD"),System.getenv(name:"TWILIO_AUTH_TOKEN"))

    //sending message
    val message = Message.creator(

        //number of the receiver
        PhoneNumber(number:"1234567890"),
        //body of your sms
        body:"hi its my first sms in kotlin"
    ).create()

    //send the message
    print(message.sid)

}