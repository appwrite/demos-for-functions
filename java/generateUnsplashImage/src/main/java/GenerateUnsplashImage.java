import java.util.HashMap;
import java.util.Map;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class GenerateUnsplashImage{

    private static String CLIENT_ID;
    private static String SEARCH_ENDPOINT = "https://api.unsplash.com/search/photos";

    public static void main(String[] args) throws UnirestException{
        CLIENT_ID = System.getenv("UNSPLASH_ACCESS_KEY");

        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("Accept-Version", "v1");

        HttpResponse<JsonNode> jsonResponse
                = Unirest.get(SEARCH_ENDPOINT)
                .queryString("client_id",CLIENT_ID)
                .queryString("query","fire")
                .queryString("page","1")
                .asJson();
        JSONObject firstResult = jsonResponse.getBody().getObject().getJSONArray("results").getJSONObject(0);
        String imageAuthor = firstResult.getJSONObject("user").getString("name");
        String imageUrl = firstResult.getJSONObject("links").getString("download");

        JSONObject result = new JSONObject();

        result.put("imageAuthor", imageAuthor);
        result.put("imageUrl", imageUrl);

        System.out.println(result.toString());
    }
}
