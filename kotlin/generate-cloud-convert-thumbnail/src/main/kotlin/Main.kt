import com.cloudconvert.client.CloudConvertClient
import com.cloudconvert.client.setttings.EnvironmentVariableSettingsProvider
import com.cloudconvert.dto.request.UploadImportRequest
import com.cloudconvert.dto.response.TaskResponse
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

    val uploadTaskRequest = UploadImportRequest()

    val uploadImportTaskResponse: TaskResponse = runCatching {
        cloudConvertClient
            .importUsing()
            .upload(uploadTaskRequest, fileStream, filename)
            .body!!
    }.getOrThrow()

//    val uploadT


    println(filename)
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