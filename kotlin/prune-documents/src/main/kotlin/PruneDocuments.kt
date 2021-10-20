import com.google.gson.Gson
import io.appwrite.Client
import io.appwrite.services.Database
import java.time.Instant
import kotlin.system.exitProcess

suspend fun main() {
    val client = Client()
        .setEndpoint(System.getenv("APPWRITE_ENDPOINT"))
        .setProject(System.getenv("APPWRITE_FUNCTION_PROJECT_ID"))
        .setKey(System.getenv("APPWRITE_API_KEY"))
    val database = Database(client)

    val secondsSinceEpoch = Instant.now().minusSeconds((5 * 365 * 24 * 60 * 60).toLong()).epochSecond

    var counter = 0

    val collectionList = database.listCollections().body?.string() ?: ""
    val collections: CollectionList = Gson().fromJson(collectionList, CollectionList::class.java)

    for (collection in collections.collections) {
        val documentsList = database
            .listDocuments(collection.`$id`, listOf("createdAt<=${secondsSinceEpoch}"))
            .body?.string() ?: ""
        val documents: DocumentList = Gson().fromJson(documentsList, DocumentList::class.java)

        for (document in documents.documents) {
            database.deleteDocument(collection.`$id`, document.`$id`)
            println("Deleted document ${document.`$id`} from collection ${collection.`$id`}")
            counter++
        }
    }

    println("Total pruned documents: $counter")
    exitProcess(0)
}
