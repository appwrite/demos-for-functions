import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class WelcomeEmail {

    private static String YOUR_DOMAIN_NAME;
    private static String API_KEY;

    public static void main(String[] args) throws UnirestException {
        YOUR_DOMAIN_NAME = System.getenv("MAILGUN_DOMAIN");
        API_KEY = System.getenv("MAILGUN_API_KEY");
        String payload = System.getenv("APPWRITE_FUNCTION_EVENT_DATA");

        if (payload != null && !payload.isEmpty()) {
            try {
                JSONObject json = new JSONObject(payload);
                String name = json.getString("name");
                String email = json.getString("email");

                String response = sendSimpleMessage(name, email);
                System.out.println(response);
            } catch (Exception e) {
                System.out.print("[ERROR] There was an error");
                System.out.println(e.getMessage());
            }

        } else {
            System.out.println("[INFO] APPWRITE_FUNCTION_EVENT_DATA is empty");
        }

    }

    public static String sendSimpleMessage(String name, String email) throws UnirestException {

        String message = String.format("Welcome %s!", name);

        HttpResponse<String> request = Unirest.post("https://api.mailgun.net/v3/" + YOUR_DOMAIN_NAME + "/messages")
                .basicAuth("api", API_KEY)
                .field("from", "Excited User <hello@appwrite.io>")
                .field("to", email)
                .field("subject", "hello")
                .field("text", message)
                .asString();

        return request.getBody();
    }
}
