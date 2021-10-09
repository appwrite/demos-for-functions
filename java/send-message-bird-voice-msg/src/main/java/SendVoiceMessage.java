import com.messagebird.exceptions.NotFoundException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.IfMachineType;
import com.messagebird.objects.VoiceType;
import com.messagebird.objects.VoiceMessageResponse;
import com.messagebird.objects.VoiceMessage;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class SendVoiceMessage {

    public static String fetchEnvironmentVariable(String variable){
        return System.getenv(variable);
    }

    public static JSONObject stringToJson(String string) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(string);
        return json;
    }

    public static void main(String[] args) throws ParseException {

        String API_KEY = fetchEnvironmentVariable("MESSAGEBIRD_API_KEY");

        String APPWRITE_FUNCTION_DATA = fetchEnvironmentVariable("APPWRITE_FUNCTION_DATA");
        JSONObject data = stringToJson(APPWRITE_FUNCTION_DATA);
        BigInteger phoneNumber = BigInteger.valueOf((Long) data.get("phoneNumber"));
        String text = (String) data.get("text");

        if (API_KEY.isEmpty() || phoneNumber.equals(null) || text.isEmpty()) {
            System.out.println("Please specify your access key, one ore more phone numbers and a message body");
            return;
        }

        // creation of service object
        final MessageBirdService messageBirdService = new MessageBirdServiceImpl(API_KEY);

        // add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);

        try {
            final List<BigInteger> phones = new ArrayList<BigInteger>();
            phones.add(phoneNumber);

            // Send a voice message using the VoiceMessage object
            final VoiceMessage voiceMessage = new VoiceMessage(text, phones);
            voiceMessage.setIfMachine(IfMachineType.cont);
            voiceMessage.setVoice(VoiceType.male);
            voiceMessage.setRepeat(2);
            final VoiceMessageResponse response = messageBirdClient.sendVoiceMessage(voiceMessage);
            System.out.println("Status: " + response.getRecipients().getItems().get(0).getStatus());

        } catch (UnauthorizedException unauthorized) {
            if (unauthorized.getErrors() != null) {
                System.out.println(unauthorized.getErrors().toString());
            }
            unauthorized.printStackTrace();
        } catch (GeneralException generalException) {
            if (generalException.getErrors() != null) {
                System.out.println(generalException.getErrors().toString());
            }
            generalException.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
