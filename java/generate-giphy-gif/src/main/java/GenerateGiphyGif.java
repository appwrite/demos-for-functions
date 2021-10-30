import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class GenerateGiphyGif {

    private static String GIPHY_API_KEY;

    public static void main(String[] args) {
        GIPHY_API_KEY = System.getenv("GIPHY_API_KEY");
        final String queryString = System.getenv("APPWRITE_FUNCTION_DATA");

        if (queryString == null) {
            System.out.println("[INFO] APPWRITE_FUNCTION_DATA is empty");
            return;
        }

        try {
            System.out.println(searchGiphyGif(queryString));
        } catch (Exception e) {
            System.out.print("[ERROR] There was an error");
            System.out.println(e.getMessage());
        }
    }

    public static String searchGiphyGif(String queryString) throws UnirestException {
        HttpResponse<JsonNode> request =
                Unirest.get("https://api.giphy.com/v1/gifs/search")
                        .queryString("api_key", GIPHY_API_KEY)
                        .queryString("q", queryString)
                        .queryString("limit", 1)
                        .asJson();

        return request.getBody().getObject().getJSONArray("data").getJSONObject(0).getString("url");
    }
}
