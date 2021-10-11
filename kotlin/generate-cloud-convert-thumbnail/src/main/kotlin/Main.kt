import io.appwrite.Client
import io.appwrite.services.Storage
import java.io.InputStream
import kotlin.system.exitProcess

suspend fun main() {
    val envVars = readEnvVars()

    val appwriteClient = with(envVars) {
        Client()
            .setEndpoint(appwriteApiEndpoint)
            .setProject(appwriteProjectId)
            .setKey(appwriteSecretKey)
    }
    val appwriteStorage = Storage(appwriteClient)

    val (
        filename: String,
        fileStream: InputStream
    ) = appwriteStorage.getFilenameAndInputStream(envVars.fileId)

    println(filename)
    exitProcess(0)
}