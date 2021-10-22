import com.google.gson.Gson
import io.appwrite.Client
import io.appwrite.services.Database
import java.time.Instant
import kotlin.system.exitProcess

const val BATCH_SIZE = 100

suspend fun main() {
    val client = Client()
        .setEndpoint(System.getenv("APPWRITE_ENDPOINT"))
        .setProject(System.getenv("APPWRITE_FUNCTION_PROJECT_ID"))
        .setKey(System.getenv("APPWRITE_API_KEY"))
    val database = Database(client)

    val secondsSinceEpoch = Instant.now().minusSeconds((5 * 365 * 24 * 60 * 60).toLong()).epochSecond

    var counter = 0

    val collections = mutableListOf<Collection>()
    var collectionOffset = 0

    while (true) {
        val rawCollectionList = database
            .listCollections(limit = BATCH_SIZE, offset = collectionOffset)
            .body?.string() ?: ""
        val collectionList: CollectionList = Gson().fromJson(rawCollectionList, CollectionList::class.java)

        if (collectionList.collections.isEmpty()) {
            break
        }

        collections.addAll(collectionList.collections)
        collectionOffset += BATCH_SIZE
    }

    for (collection in collections) {
        val documents = mutableListOf<Document>()
        var documentOffset = 0

        while (true) {
            val rawDocumentsList = database
                .listDocuments(collection.`$id`, listOf("createdAt<=${secondsSinceEpoch}"), BATCH_SIZE, documentOffset)
                .body?.string() ?: ""
            val documentList: DocumentList = Gson().fromJson(rawDocumentsList, DocumentList::class.java)

            if (documentList.documents.isEmpty()) {
                break
            }

            documents.addAll(documentList.documents)
            documentOffset += BATCH_SIZE
        }

        for (document in documents) {
            database.deleteDocument(collection.`$id`, document.`$id`)
            println("Deleted document ${document.`$id`} from collection ${collection.`$id`}")
            counter++
        }
    }

    println("Total pruned documents: $counter")
    exitProcess(0)
}
