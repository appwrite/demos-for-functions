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

fun Client.configureFrom(envVars: EnvVars) = with(envVars) {
    setEndpoint(appwriteApiEndpoint)
        .setProject(appwriteProjectId)
        .setKey(appwriteSecretKey)
}

suspend fun Storage.getFilenameAndInputStream(fileId: String): Pair<String, InputStream> =
    try {
        val fileStream = getFileView(fileId).body?.byteStream()!!
        val response = getFile(fileId).body?.string()!!
        val name = Gson().fromJson(response, AppwriteFile::class.java)!!.name

        name to fileStream
    } catch (e: NullPointerException) {
        System.err.println("[ERR] Unable to read file with id: $fileId")
        throw(e)
    }

suspend fun Storage.createFromUrl(filename: String, url: String): AppwriteFile {
    val file = File(filename)

    HttpClient().use { httpClient ->
        val httpResponse: HttpResponse = httpClient.get(url)
        val responseBody: ByteArray = httpResponse.receive()
        file.writeBytes(responseBody)
    }

    val appwriteFile = createFile(file).body?.string()!!.run {
        Gson().fromJson(this, AppwriteFile::class.java)!!
    }

    file.deleteOnExit()

    return appwriteFile
}