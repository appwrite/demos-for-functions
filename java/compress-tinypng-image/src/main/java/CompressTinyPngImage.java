import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;

public class CompressTinyPngImage {

    private static String TINYPNG_API_KEY;
    private static String APPWRITE_ENDPOINT;
    private static String APPWRITE_FUNCTION_PROJECT_ID;
    private static String APPWRITE_API_KEY;

    public static void main(String[] args) {
        TINYPNG_API_KEY = System.getenv("TINYPNG_API_KEY");
        APPWRITE_ENDPOINT = System.getenv("APPWRITE_ENDPOINT");
        APPWRITE_FUNCTION_PROJECT_ID = System.getenv("APPWRITE_FUNCTION_PROJECT_ID");
        APPWRITE_API_KEY = System.getenv("APPWRITE_API_KEY");
        final String imageUrl = System.getenv("APPWRITE_FUNCTION_DATA");

        if (imageUrl == null || imageUrl.isBlank()) {
            System.out.println("[INFO] APPWRITE_FUNCTION_DATA is empty");
            return;
        }

        try {
            String location = compressImage(imageUrl);
            File file = downloadResult(location);
            storeToAppWrite(file);
        } catch (Exception e) {
            System.out.print("[ERROR] There was an error");
            System.out.println(e.getMessage());
        }
    }

    private static String compressImage(String imageUrl) throws UnirestException {
        String requestBody = String.format("{\"source\":{\"url\":\"%s\"}}", imageUrl);
        HttpResponse<JsonNode> request =
                Unirest.post("https://api.tinify.com/shrink")
                        .header("Authorization", getAuthorization())
                        .header("Content-Type", "application/json")
                        .body(new JsonNode(requestBody))
                        .asJson();

        return request.getHeaders().get("Location").get(0);
    }

    private static File downloadResult(String location) throws UnirestException, IOException {
        HttpResponse<InputStream> download =
                Unirest.get(location)
                        .header("Authorization", getAuthorization())
                        .asBinary();

        File targetFile = new File(String.format("CompressTinyPngImage_%s.png", Instant.now().toEpochMilli()));
        FileUtils.copyInputStreamToFile(download.getBody(), targetFile);

        return targetFile;
    }

    private static String getAuthorization() {
        return String.format("Basic %s", new String(Base64.encodeBase64(String.format("api:%s", TINYPNG_API_KEY).getBytes())));
    }

    private static void storeToAppWrite(File file) throws UnirestException {
        HttpResponse<JsonNode> request =
                Unirest.post(String.format("%s/storage/files", APPWRITE_ENDPOINT))
                        .header("X-Appwrite-Project", APPWRITE_FUNCTION_PROJECT_ID)
                        .header("X-Appwrite-key", APPWRITE_API_KEY)
                        .field("file", file)
                        .asJson();
        System.out.println(request.getBody());
    }

}
