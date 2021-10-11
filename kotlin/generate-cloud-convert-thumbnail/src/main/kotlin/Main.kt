
import com.cloudconvert.client.CloudConvertClient
import com.cloudconvert.client.setttings.EnvironmentVariableSettingsProvider
import io.appwrite.Client
import io.appwrite.services.Storage
import java.io.InputStream
import kotlin.system.exitProcess

suspend fun main() {
    val envVars = readEnvVars()

    val appwriteClient = Client().setEnvVars(envVars)
    val appwriteStorage = Storage(appwriteClient)
    val cloudConvertClient = createCloudConvertClient()

    val (filename: String, fileStream: InputStream) = appwriteStorage.getFilenameAndInputStream(envVars.fileId)

    val createJobResponse = cloudConvertClient.createJob()
    cloudConvertClient.uploadFile(createJobResponse, fileStream, filename)
    val exportUrl = cloudConvertClient.getExportedUrl(createJobResponse)

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