
import com.cloudconvert.client.CloudConvertClient
import com.cloudconvert.client.setttings.EnvironmentVariableSettingsProvider
import com.cloudconvert.dto.response.TaskResponse
import io.appwrite.Client
import io.appwrite.services.Storage
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import util.*
import kotlin.system.exitProcess

private const val FILE_LIST_KEY = "APPWRITE_FUNCTION_DATA"
private const val APPWRITE_ENDPOINT_KEY = "APPWRITE_ENDPOINT"
private const val APPWRITE_PROJECT_ID_KEY = "APPWRITE_FUNCTION_PROJECT_ID"
private const val APPWRITE_SECRETKEY_KEY = "APPWRITE_API_KEY"

suspend fun main() {
    val appwriteClient = Client()
        .setEndpoint(Env.readString(APPWRITE_ENDPOINT_KEY))
        .setProject(Env.readString(APPWRITE_PROJECT_ID_KEY))
        .setKey(Env.readString(APPWRITE_SECRETKEY_KEY))

    val appwriteStorage = Storage(appwriteClient)

    val jsonParser = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }

    val cloudConvertClient = createCloudConvertClient()

    val fileIds = Env.read(FILE_LIST_KEY) { it.split(" ") }
        .map { it.trim() }

    val archiveJob = cloudConvertClient.createArchiveJob(fileIds, "zip")

    archiveJob.uploadFileTasks().map { task: TaskResponse ->
        task.uploadFile(appwriteStorage, jsonParser, cloudConvertClient)
    }.collect() // to materialize the flow

    val exportedFileUrl = cloudConvertClient.exportedFileUrlFor(archiveJob.id)

    val archive = appwriteStorage.createFromUrl(
        filename = "archived--${fileIds.joinToString("-")}.zip",
        url = exportedFileUrl,
        jsonParser = jsonParser
    )

    println(archive.id)
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