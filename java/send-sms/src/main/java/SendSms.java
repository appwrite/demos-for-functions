// Install the Java helper library from twilio.com/docs/java/install

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.json.JSONObject;

public class SendSms {
    // Getting the user credentials
    public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    public static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    public static final String SENDER = System.getenv("TWILIO_PHONE_NUMBER");

    public static void main(String[] args) {
        String payload = System.getenv("APPWRITE_FUNCTION_EVENT_DATA");

        if (payload != null && !payload.isEmpty()) {
            try {
                JSONObject json = new JSONObject(payload);
                String receiver = json.getString("receiver");
                String msg = json.getString("msg");

                sendMessage(receiver, msg);
            } catch (Exception e) {
                System.out.print("[ERROR] There was an error");
                System.out.println(e.getMessage());
            }

        } else {
            System.out.println("[INFO] APPWRITE_FUNCTION_EVENT_DATA is empty");
        }
    }

    public static void sendMessage(String receiver, String msg) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message
                .creator(new com.twilio.type.PhoneNumber(receiver), new com.twilio.type.PhoneNumber(SENDER), msg)
                .create();

        System.out.println(message.getSid());
    }
}
