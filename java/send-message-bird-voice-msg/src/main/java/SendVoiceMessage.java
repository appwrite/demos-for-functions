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
    //fetch api key, phone number and message
    //make api call
    //return status

    public static String fetchEnvironmentVariable(String variable){
        return System.getenv(variable);
    }

    public static JSONObject stringToJson(String string) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(string);
        return json;
    }



    public static void main(String[] args) throws ParseException {

//        String API_KEY = fetchEnvironmentVariable("MESSAGEBIRD_API_KEY");
//
//        String APPWRITE_FUNCTION_DATA = fetchEnvironmentVariable("APPWRITE_FUNCTION_DATA");
//        JSONObject data = stringToJson(APPWRITE_FUNCTION_DATA);
//        BigInteger phoneNumber = (BigInteger) data.get("phoneNumber");
//        String text = (String) data.get("text");

        String API_KEY = "iKS6mXNFYOeleTyIWvLl1SWBQ";

        BigInteger phoneNumber =  new BigInteger("+918390390061");
        String text = "Hello this is a test call from message bird";

        if (API_KEY.isEmpty() || phoneNumber.equals(null) || text.isEmpty()) {
            System.out.println("Please specify your access key, one ore more phone numbers and a message body");
            return;
        }

        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl(API_KEY);

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            // Get Hlr using msgId and msisdn
            System.out.println("Sending message:");
            final List<BigInteger> phones = new ArrayList<BigInteger>();
            phones.add(phoneNumber);

            // Send a voice message using the VoiceMessage object
            final VoiceMessage vm = new VoiceMessage(text, phones);
            vm.setIfMachine(IfMachineType.cont);
            vm.setVoice(VoiceType.male);
            vm.setRepeat(2);
            final VoiceMessageResponse response = messageBirdClient.sendVoiceMessage(vm);
            System.out.println(response.getRecipients().getItems().get(0).getStatus());
            System.out.println(response.toString());

//            final VoiceMessageResponse statusResponse = messageBirdClient.viewVoiceMessage(response.getId());
//            System.out.println(response.toString());

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
