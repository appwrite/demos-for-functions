import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class Main {

    public static void main(String[] args){

        String url = System.getenv("APPWRITE_FUNCTION_DATA");
        String token = System.getenv("BITLY_ACCESS_TOKEN");

        generateUrl(url, token, response -> {
            System.out.println(response);
        });

    }

    public static void generateUrl(String urlToGenerate, String token, Consumer<String> callback){

        try{
            URL url = new URL("https://api-ssl.bitly.com/v4/shorten");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setRequestProperty("Authorization", "Bearer " + token);
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Accept", "application/json");

            String data = "{\n\"long_url\": \"" + urlToGenerate + "\"\n}";

            byte[] out = data.getBytes(StandardCharsets.UTF_8);

            OutputStream stream = http.getOutputStream();
            stream.write(out);

            BufferedReader br = new BufferedReader( new InputStreamReader(http.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            JsonObject jsonObject = new JsonParser().parse(response.toString()).getAsJsonObject();
            callback.accept(jsonObject.get("link").getAsString());
            http.disconnect();
        }
        catch (Exception ex){
            callback.accept("Error: " + ex.getMessage());
        }

    }

}