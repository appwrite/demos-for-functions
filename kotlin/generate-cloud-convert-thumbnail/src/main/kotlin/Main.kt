import io.appwrite.Client
import io.appwrite.services.Storage
import kotlin.system.exitProcess

suspend fun main() {
    val envVars = readEnvVars()

    val appwriteClient = with (envVars) {
        Client()
            .setEndpoint(appwriteApiEndpoint)
            .setProject(appwriteProjectId)
            .setKey(appwriteSecretKey)
    }

    val appwriteStorage = Storage(appwriteClient)

    println("fetching data")

    val result = appwriteStorage.getFileView(envVars.fileId)

    println(result)
    exitProcess(0)
}