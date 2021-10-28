import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import io.appwrite.exceptions.AppwriteException;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

class BackupToStorage {
    public static void main(String[] args) throws IOException, AppwriteException {
        FileWriter collectionsCsv = null;
        String headers = "collection_id, permissions, name, date_created, date_updated, rules";
        try {
            String filePath = System.getProperty("user.dir") + "/collections.csv";
            collectionsCsv = new FileWriter(filePath);

            collectionsCsv.append(headers);
            collectionsCsv.append('\n');

            HttpResponse<JsonNode> response =
                    Unirest.get(String.format("%s/database/collections", System.getenv("APPWRITE_ENDPOINT")))
                            .header("X-Appwrite-Project", System.getenv("APPWRITE_FUNCTION_PROJECT_ID"))
                            .header("X-Appwrite-key", System.getenv("APPWRITE_API_KEY"))
                            .asJson();


            JSONObject responeJson = response.getBody().getObject();
            JSONArray collections = responeJson.getJSONArray("collections");

            for (int i=0; i < collections.length(); i++) {
                JSONObject jsonCollection = collections.getJSONObject(i);

                collectionsCsv.append(jsonCollection.getString("$id"));
                collectionsCsv.append(",");
                collectionsCsv.append(jsonCollection.getJSONObject("$permissions").toString());
                collectionsCsv.append(",");
                collectionsCsv.append(jsonCollection.getString("name"));
                collectionsCsv.append(",");
                collectionsCsv.append((char) jsonCollection.getLong("dateCreated"));
                collectionsCsv.append(",");
                collectionsCsv.append((char) jsonCollection.getLong("dateUpdated"));
                collectionsCsv.append(",");
                collectionsCsv.append(jsonCollection.getJSONArray("rules").toString());
                collectionsCsv.append("\n");
            }

            HttpResponse<JsonNode> storageResponse =
                    Unirest.get(String.format("%s/storage/files", System.getenv("APPWRITE_ENDPOINT")))
                            .header("X-Appwrite-Project", System.getenv("APPWRITE_FUNCTION_PROJECT_ID"))
                            .header("X-Appwrite-key", System.getenv("APPWRITE_API_KEY"))
                            .asJson();


            if (storageResponse.getStatus() >= 200) {
                System.out.println("Successfully uploaded collections.csv to storage");
            }

        } catch (Throwable th) {
            System.out.println(th);
        } finally {
            if (collectionsCsv != null) {
                collectionsCsv.flush();
            }
            System.exit(0);
        }
    }
}
