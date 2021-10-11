import com.cloudconvert.client.CloudConvertClient
import com.cloudconvert.client.setttings.EnvironmentVariableSettingsProvider
import com.cloudconvert.dto.response.TaskResponse
import com.google.gson.Gson
import io.appwrite.Client
import io.appwrite.services.Storage
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import model.AppwriteFile
import java.io.File
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

    val exportFileDetails = cloudConvertClient.exportFileDetailsFor(thumbnailGenerationJob.id)

    val thumbnail = File(exportFileDetails.filename)

    HttpClient().use { httpClient ->
        val httpResponse: HttpResponse = httpClient.get(exportFileDetails.url)
        val responseBody: ByteArray = httpResponse.receive()
        thumbnail.writeBytes(responseBody)
    }

    val appwriteFile = appwriteStorage.createFile(thumbnail).body?.string()!!.run {
        Gson().fromJson(this, AppwriteFile::class.java)!!
    }

    thumbnail.deleteOnExit()

    println(appwriteFile.`$id`)
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
    }.getOrElse {
        System.err.println("[ERR] Failed to upload file to cloudConvert")
        throw it
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