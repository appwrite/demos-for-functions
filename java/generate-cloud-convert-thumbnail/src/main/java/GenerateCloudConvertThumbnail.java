import com.cloudconvert.client.CloudConvertClient;
import com.cloudconvert.client.setttings.EnvironmentVariableSettingsProvider;
import com.cloudconvert.dto.request.CreateThumbnailsTaskRequest;
import com.cloudconvert.dto.request.UploadImportRequest;
import com.cloudconvert.dto.request.UrlExportRequest;
import com.cloudconvert.dto.response.JobResponse;
import com.cloudconvert.dto.response.TaskResponse;
import com.google.common.collect.ImmutableMap;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

public class GenerateCloudConvertThumbnail {

    private static String APPWRITE_ENDPOINT;
    private static String APPWRITE_FUNCTION_PROJECT_ID;
    private static String APPWRITE_API_KEY;

    public static void main(String[] args) throws IOException {
        // setup
        APPWRITE_ENDPOINT = System.getenv("APPWRITE_ENDPOINT");
        APPWRITE_FUNCTION_PROJECT_ID = System.getenv("APPWRITE_FUNCTION_PROJECT_ID");
        APPWRITE_API_KEY = System.getenv("APPWRITE_API_KEY");
        final String fileId = System.getenv("APPWRITE_FUNCTION_DATA");

        CloudConvertClient client = new CloudConvertClient(new EnvironmentVariableSettingsProvider());

        // validate input data
        if (fileId == null || fileId.isBlank()) {
            System.out.println("[INFO] APPWRITE_FUNCTION_DATA is empty");
            return;
        }

        // execute
        try {
            String fileName = getAppWriteFileName(fileId);
            InputStream inputStream = downloadFromAppWrite(fileId);
            File thumbnail = createThumbnail(client, inputStream, fileName);
            System.out.println(storeToAppWrite(thumbnail));
        } catch (Exception e) {
            System.out.print("[ERROR] There was an error");
            System.out.println(e.getMessage());
        }

    }

    private static File createThumbnail(CloudConvertClient client, InputStream inputStream, String fileName) throws IOException, URISyntaxException {
        // Create a job
        final String IMPORT_FILE_KEY = "import-file";
        final String CREATE_THUMBNAIL_KEY = "create-thumbnail";
        final String EXPORT_FILE_KEY = "export-file";

        final JobResponse jobResponse = client.jobs().create(
                ImmutableMap.of(
                        IMPORT_FILE_KEY, new UploadImportRequest(),
                        CREATE_THUMBNAIL_KEY, new CreateThumbnailsTaskRequest()
                                .setInput(IMPORT_FILE_KEY)
                                .setOutputFormat("png"),
                        EXPORT_FILE_KEY, new UrlExportRequest().setInput(CREATE_THUMBNAIL_KEY)
                )
        ).getBody();

        // Upload
        final TaskResponse taskResponse = getTaskResponse(IMPORT_FILE_KEY, jobResponse);
        client.importUsing().upload(taskResponse.getId(), taskResponse.getResult().getForm(), inputStream, fileName);

        // Wait for a job completion
        final JobResponse waitJobResponse = client.jobs().wait(jobResponse.getId()).getBody();

        // Get an export/url task id
        final String exportUrlTaskId = getTaskResponse(EXPORT_FILE_KEY, waitJobResponse).getId();

        // Wait for an export/url task to be finished
        final TaskResponse waitUrlExportTaskResponse = client.tasks().wait(exportUrlTaskId).getBody();

        // Get url and filename of export/url task
        final String exportUrl = waitUrlExportTaskResponse.getResult().getFiles().get(0).get("url");

        // Get file as input stream using url of export/url task
        final InputStream thumbnailInputStream = client.files().download(exportUrl).getBody();

        File thumbnail = new File(fileName + ".png");
        FileUtils.copyInputStreamToFile(thumbnailInputStream, thumbnail);
        return thumbnail;
    }

    @NotNull
    private static TaskResponse getTaskResponse(String IMPORT_FILE_KEY, JobResponse jobResponse) {
        return jobResponse.getTasks().stream()
                .filter(task -> task.getName().equals(IMPORT_FILE_KEY))
                .findFirst().get();
    }

    private static String getAppWriteFileName(String fileId) throws UnirestException {
        HttpResponse<JsonNode> request =
                Unirest.get(String.format("%s/storage/files/%s", APPWRITE_ENDPOINT, fileId))
                        .header("X-Appwrite-Project", APPWRITE_FUNCTION_PROJECT_ID)
                        .header("X-Appwrite-key", APPWRITE_API_KEY)
                        .asJson();
        return request.getBody().getObject().getString("name");
    }

    private static InputStream downloadFromAppWrite(String fileId) throws UnirestException {
        HttpResponse<InputStream> request =
                Unirest.get(String.format("%s/storage/files/%s/view", APPWRITE_ENDPOINT, fileId))
                        .header("X-Appwrite-Project", APPWRITE_FUNCTION_PROJECT_ID)
                        .header("X-Appwrite-key", APPWRITE_API_KEY)
                        .asBinary();
        return request.getBody();
    }

    private static String storeToAppWrite(File file) throws UnirestException {
        HttpResponse<JsonNode> request =
                Unirest.post(String.format("%s/storage/files", APPWRITE_ENDPOINT))
                        .header("X-Appwrite-Project", APPWRITE_FUNCTION_PROJECT_ID)
                        .header("X-Appwrite-key", APPWRITE_API_KEY)
                        .field("file", file)
                        .asJson();
        return request.getBody().getObject().getString("$id");
    }
}
