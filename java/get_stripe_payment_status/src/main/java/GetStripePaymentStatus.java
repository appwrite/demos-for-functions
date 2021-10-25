import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GetStripePaymentStatus {

    public static String fetchEnvironmentVariable(String variable){
        return System.getenv(variable);
    }

    public static JSONObject stringToJson(String string) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(string);
        return json;
    }

    public static void main(String[] args) {
        Stripe.apiKey = fetchEnvironmentVariable("STRIPE_API_KEY");
        String functionData = fetchEnvironmentVariable("APPWRITE_FUNCTION_DATA");
        try {
            String stripePaymentId = (String) stringToJson(functionData).get("stripePaymentId");
            PaymentIntent paymentIntent = PaymentIntent.retrieve(
                    stripePaymentId
            );
            System.out.println(paymentIntent.getStatus());
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (StripeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
