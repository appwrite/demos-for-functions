import com.google.gson.Gson
import io.appwrite.Client
import io.appwrite.services.Storage
import java.util.*
import kotlin.system.exitProcess

suspend fun main(args: Array<String>) {
    val client = Client()
        .setEndpoint(System.getenv("APPWRITE_ENDPOINT"))
        .setProject(System.getenv("APPWRITE_FUNCTION_PROJECT_ID"))
        .setKey(System.getenv("APPWRITE_API_KEY"))
    val storage = Storage(client)

    val daysToExpire = System.getenv("DAYS_TO_EXPIRE").toFloatOrNull()
    if (daysToExpire == null) {
        println("Unable to parse DAYS_TO_EXPIRE.")
        exitProcess(1)
    }

    val fileList = storage.listFiles("",100, 0, "DESC").body?.string() ?: ""
    val files: FileList = Gson().fromJson(fileList, FileList::class.java)

    var deletedFiles =  0
    for( file in files.files ?: listOf() ) {
        val diff: Long = Date().time/1000 - file.dateCreated
        if (diff > daysToExpire * 24 * 60 * 60) {
            storage.deleteFile(file.`$id`)
            println("Deleted ${file.`$id`}")
            deletedFiles++
        }
    }
    println("Total files deleted: $deletedFiles")
    exitProcess(0)
}