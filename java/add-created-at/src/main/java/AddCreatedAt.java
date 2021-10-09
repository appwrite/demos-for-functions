import io.appwrite.Client;
import io.appwrite.exceptions.AppwriteException;
import io.appwrite.services.Database;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;

public class AddCreatedAt {
    public static void main(String[] args) throws AppwriteException {
        JSONObject payloadJSON = AddCreatedAt.getPayload();
        String collectionId = payloadJSON.getString("collectionId");
        String documentId = payloadJSON.getString("documentId");
        Database database = AddCreatedAt.initDatabase();
        HashMap<String, String> data = AddCreatedAt.generateCreatedAtTimestamp();

        Continuation<Response> continuation = new Continuation<Response>() {
            @NotNull
            @Override
            public CoroutineContext getContext() {
                return EmptyCoroutineContext.INSTANCE;
            }

            @Override
            public void resumeWith(@NotNull Object o) {
                System.out.println(o.toString());
                Runtime.getRuntime().halt(0);
            }
        };


        database.updateDocument(collectionId,
                documentId, data, continuation);
    }

    private static JSONObject getPayload() {
        String payload = System.getenv("APPWRITE_FUNCTION_DATA");

        return new JSONObject(payload);
    }

    private static Database initDatabase() {
        Database database = new Database(AddCreatedAt.initializeClient());

        return database;
    }

    private static Client initializeClient() {
        String APPWRITE_ENDPOINT = System.getenv("APPWRITE_ENDPOINT");
        String APPWRITE_FUNCTION_PROJECT_ID = System.getenv("APPWRITE_FUNCTION_PROJECT_ID");
        String APPWRITE_API_KEY = System.getenv("APPWRITE_API_KEY");

        Client client = new Client();
        client.setEndPoint(APPWRITE_ENDPOINT);
        client.setProject(APPWRITE_FUNCTION_PROJECT_ID);
        client.setKey(APPWRITE_API_KEY);

        return client;
    }

    private static HashMap<String, String> generateCreatedAtTimestamp() {
        String createdAt = Timestamp.from(Instant.now()).toString();
        HashMap<String, String> createdAtMap = new HashMap<String, String>();

        createdAtMap.put("createdAt",createdAt);

        return createdAtMap;
    }
}
