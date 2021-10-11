import com.google.gson.Gson
import io.appwrite.Client
import io.appwrite.services.Storage
import model.File
import java.io.InputStream

fun Client.setEnvVars(envVars: EnvVars) = with(envVars) {
    setEndpoint(appwriteApiEndpoint)
        .setProject(appwriteProjectId)
        .setKey(appwriteSecretKey)
}

suspend fun Storage.getFilenameAndInputStream(fileId: String): Pair<String, InputStream> =
    try {
        val fileStream = getFileView(fileId).body?.byteStream()!!
        val response = getFile(fileId).body?.string()!!
        val name = Gson().fromJson(response, File::class.java)!!.name

        name to fileStream
    } catch (e: NullPointerException) {
        System.err.println("[ERR] Unable to read file with id: $fileId")
        throw(e)
    }