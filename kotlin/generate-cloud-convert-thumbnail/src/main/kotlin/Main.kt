import com.cloudconvert.client.CloudConvertClient
import com.cloudconvert.client.setttings.EnvironmentVariableSettingsProvider
import com.cloudconvert.dto.response.TaskResponse
import io.appwrite.Client
import io.appwrite.services.Storage
import java.io.InputStream
import kotlin.system.exitProcess

suspend fun main() {
    val envVars = readEnvVars()

    val appwriteClient = Client().configureFrom(envVars)
    val appwriteStorage = Storage(appwriteClient)
    val cloudConvertClient = createCloudConvertClient()

    val thumbnailGenerationJob = cloudConvertClient.createThumbnailGenerationJob()
    val uploadFileTask = thumbnailGenerationJob.uploadFileTask()

    uploadFileToCloudConvert(
        appwriteStorage = appwriteStorage,
        appwriteFileId = envVars.fileId,
        cloudConvertClient = cloudConvertClient,
        uploadFileTask = uploadFileTask
    )

    val exportUrl = cloudConvertClient.exportUrlFor(thumbnailGenerationJob.id)

    println(exportUrl)
    exitProcess(0)
}

private suspend fun uploadFileToCloudConvert(
    appwriteStorage: Storage,
    appwriteFileId: String,
    uploadFileTask: TaskResponse,
    cloudConvertClient: CloudConvertClient
) {
    val (filename: String, fileStream: InputStream) =
        appwriteStorage.getFilenameAndInputStream(appwriteFileId)
    uploadFileTask.runCatching {
        cloudConvertClient.importUsing().upload(id!!, result.form!!, fileStream, filename)
    }
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