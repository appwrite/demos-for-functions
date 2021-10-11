
import com.cloudconvert.client.CloudConvertClient
import com.cloudconvert.client.setttings.EnvironmentVariableSettingsProvider
import com.cloudconvert.dto.request.CreateThumbnailsTaskRequest
import com.cloudconvert.dto.request.UploadImportRequest
import com.cloudconvert.dto.request.UrlExportRequest
import io.appwrite.Client
import io.appwrite.services.Storage
import java.io.InputStream
import kotlin.system.exitProcess


suspend fun main() {
    val envVars = readEnvVars()

    val appwriteClient = Client().setEnvVars(envVars)
    val appwriteStorage = Storage(appwriteClient)

    val cloudConvertClient = createCloudConvertClient()

    val (
        filename: String,
        fileStream: InputStream
    ) = appwriteStorage.getFilenameAndInputStream(envVars.fileId)

    println("filename: $filename")

    val createJobResponse = cloudConvertClient.jobs().create(
        mapOf(
            "import-file" to UploadImportRequest(),
            "create-thumbnail" to CreateThumbnailsTaskRequest()
                .setInput("import-file")
                .setOutputFormat("png")
                .set<CreateThumbnailsTaskRequest>("width", 400)
                .set("height", 400),
            "export-file" to UrlExportRequest().setInput("create-thumbnail")
        )
    ).body!!

    val uploadTask = createJobResponse
        .tasks
        .stream()
        .filter { it.name.equals("import-file") }.findFirst()
        .get()

    runCatching {
        cloudConvertClient
            .importUsing()
            .upload(uploadTask.id!!, uploadTask.result.form!!, fileStream, filename)
            .body!!
    }.getOrElse {
        System.err.println("Failed to upload/convert file")
        throw(it)
    }

    val waitJobResponse = cloudConvertClient
        .jobs()
        .wait(createJobResponse.id)
        .body!!

    val exportTaskMap =
        waitJobResponse
            .tasks
            .stream()
            .filter { it.name.equals("export-file") }.findFirst()
            .get()
            .result
            .files[0]

    val exportUrl = exportTaskMap["url"]!!
    println(exportUrl)

    exitProcess(0)
}

fun createCloudConvertClient() = runCatching {
    CloudConvertClient(EnvironmentVariableSettingsProvider())
}.getOrElse {
    System.err.println(
        """[ERR] Failed to initialize Cloud Convert Client.
                |Is your environment configured correctly?
                |https://github.com/cloudconvert/cloudconvert-java
                |
                |""".trimMargin()
    )
    throw(it)
}