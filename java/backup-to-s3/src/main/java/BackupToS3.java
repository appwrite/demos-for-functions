import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.appwrite.Client;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.util.ArrayList;

class BackupToS3 {
    String projectId;
    AmazonS3Client s3;
    String filePath;
    CSVPrinter csvPrinter;
    String appWriteApiKey;
    String appWriteEndpoint;
    Client appWriteclient;
    String s3Bucket;

    BackupToS3() throws IOException {
        this.projectId = System.getenv("APPWRITE_FUNCTION_PROJECT_ID");

        this.filePath = System.getProperty("user.dir") + "/" + this.projectId + "_collections.csv";
        this.csvPrinter = new CSVPrinter(new BufferedWriter(new FileWriter(this.filePath)), CSVFormat.DEFAULT
                .withHeader("collection_id", "collection_name", "permissions_read", "permissions_write", "rules",
                        "name", "age", "date_created", "date_updated"
                ));

        this.appWriteEndpoint = System.getenv("APPWRITE_ENDPOINT");
        this.appWriteApiKey = System.getenv("APPWRITE_API_KEY");

        this.appWriteclient = new Client()
                .setEndpoint(this.appWriteEndpoint)
                .setProject(this.projectId)
                .setKey(this.appWriteApiKey);

        this.s3 = new AmazonS3Client(new BasicAWSCredentials(
                System.getenv("AWS_API_KEY"),
                System.getenv("AWS_API_SECRET")));

        this.s3Bucket = System.getenv("BUCKET_NAME");
    }

    public void backupCollectionsToS3() {
        try {
            JSONArray collectionsList = getAllCollections();

            for (int i=0; i<collectionsList.length(); i++) {
                JSONObject collection = collectionsList.getJSONObject(i);
                JSONArray documentsList = getAllDocuments(collection.getString("$id"));
                System.out.println(collectionsList);
                System.out.println(documentsList);

                for (int j=0; j< documentsList.length(); j++) {
                    JSONObject document = documentsList.getJSONObject(j);
                    writeToCsv(collection.getString("$id"), collection.getString("name"),
                        collection.getJSONObject("$permissions").toString(), collection.getJSONObject("$permissions").toString(),
                        getRules(collection.getJSONArray("rules")), document.getString("name"),
                        document.getString("age"), collection.getLong("dateCreated"), collection.getLong("dateUpdated")
                    );
                }
            }
            csvPrinter.flush();
            csvPrinter.close();
            uploadToS3();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            System.exit(0);
        }
    }

    private JSONArray getAllCollections() throws UnirestException {
        int limit = 100;
        JSONObject response = getCollectionsWithOffset(limit, 0);
        JSONArray collectionList = response.getJSONArray("collections");

        int sum = response.getInt("sum");
        int pages = (sum/limit);

        if(pages > 0) {
            int page = 1;
            while (page < pages) {
                JSONObject pageResponse = getCollectionsWithOffset(limit, page);
                JSONArray collectionsResponse = pageResponse.getJSONArray("collections");

                for (int i=0; i<collectionsResponse.length(); i++) {
                    collectionList.put(collectionsResponse.getJSONObject(i));
                }
                page += 1;
            }
        }

        return collectionList;
    }

    private JSONObject getCollectionsWithOffset(Integer limit, Integer page) throws UnirestException {
        int offset = page * limit;
        String url = "%s/database/collections?limit="+limit;

        if (page != 0) {
            url += "&offset="+offset;
        }

        HttpResponse<JsonNode> response =
                Unirest.get(String.format(url, this.appWriteEndpoint))
                        .header("X-Appwrite-Project", this.projectId)
                        .header("X-Appwrite-key", this.appWriteApiKey)
                        .asJson();

        return response.getBody().getObject();
    }

    private JSONArray getAllDocuments(String collectionId) throws UnirestException {
        int limit = 100;
        JSONObject response = getDocumentsWithOffset(collectionId, limit, 0);
        JSONArray documentsList = response.getJSONArray("documents");

        int sum = response.getInt("sum");
        int pages = (sum/limit);

        if(pages > 0) {
            int page = 1;
            while (page < pages) {
                JSONObject pageResponse = getDocumentsWithOffset(collectionId, limit, page);
                JSONArray documentsResponse = pageResponse.getJSONArray("documents");

                for (int i=0; i<documentsResponse.length(); i++) {
                    documentsList.put(documentsResponse.getJSONObject(i));
                }
                page += 1;
            }
        }

        return documentsList;
    }

    private JSONObject getDocumentsWithOffset(String collectionId, Integer limit, Integer page) throws UnirestException {
        int offset = page * limit;
        String url = "%s/database/collections/"+collectionId+"/documents?limit="+limit;

        if (page != 0) {
            url += "&offset="+offset;
        }

        HttpResponse<JsonNode> response =
                Unirest.get(String.format(url, this.appWriteEndpoint))
                        .header("X-Appwrite-Project", this.projectId)
                        .header("X-Appwrite-key", this.appWriteApiKey)
                        .asJson();

        return response.getBody().getObject();
    }

    private String getRules(JSONArray rules) {
        ArrayList rulesData = new ArrayList();

        for (int i=0; i<rules.length(); i++) {
            JSONObject rule = rules.getJSONObject(i);
            rulesData.add(rule.getString("$id") + " " + rule.getString("type") + " " + rule.getString("key") + " " + rule.getString("default"));
        }

        return rulesData.toString();
    }

    private void writeToCsv(String collectionId, String collectionName, String readPermissions, String writePermissions,
                            String rules, String name, String age, Long createdAt, Long updatedAt) throws IOException {
        if (name == null) {
            name = "";
        }
        if (age == null) {
            age = "";
        }
        csvPrinter.printRecord(collectionId, collectionName, readPermissions, writePermissions, rules, name, age,
                createdAt.toString(), updatedAt.toString()
        );
    }

    private void uploadToS3() throws FileNotFoundException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType("text/csv");

        PutObjectResult s3UploadResponse = this.s3.putObject(
                s3Bucket,
                "collections_" + this.projectId,
                new FileInputStream(this.filePath),
                objectMetadata
        );

        if (s3UploadResponse != null) {
            System.out.println("Successfully uploaded collections.csv to S3");
        }
    }
}
