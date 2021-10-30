import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.MessageResponse;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.List;

public class SendMessageBirdSMS {

    private static String MESSAGE_BIRD_API_KEY;

    public static void main(String[] args) {
        MESSAGE_BIRD_API_KEY = System.getenv("MESSAGE_BIRD_API_KEY");
        final String payload = System.getenv("APPWRITE_FUNCTION_DATA");

        if (payload == null || payload.isBlank()) {
            System.out.println("[INFO] APPWRITE_FUNCTION_DATA is empty");
            return;
        }

        try {
            JSONObject json = new JSONObject(payload);
            String phoneNumber = json.getString("phoneNumber");
            String text = json.getString("text");

            String response = sendMessageBirdSMS(phoneNumber, text);
            System.out.println(response);
        } catch (Exception e) {
            System.out.print("[ERROR] There was an error");
            System.out.println(e.getMessage());
        }
    }

    public static String sendMessageBirdSMS(String phoneNumber, String text) throws GeneralException, UnauthorizedException {
        final MessageBirdService messageBirdService = new MessageBirdServiceImpl(MESSAGE_BIRD_API_KEY);
        final MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);

        final MessageResponse response = messageBirdClient.sendMessage("MessageBird", text, List.of(BigInteger.valueOf(Long.parseLong(phoneNumber))));
        return response.toString();
    }
}
