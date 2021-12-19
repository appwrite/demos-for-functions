import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.S3ClientOptions
import com.amazonaws.services.s3.model.ObjectMetadata
import com.google.gson.Gson
import io.appwrite.Client
import io.appwrite.services.Database
import kotlinx.coroutines.runBlocking
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import kotlin.system.exitProcess


class BackupToS3 {
    companion object {
        private val projectId = System.getenv("APPWRITE_FUNCTION_PROJECT_ID")

        private var filePath = "${System.getProperty("user.dir")}/${projectId}_collections.csv"
        val csvPrinter = CSVPrinter(BufferedWriter(FileWriter(filePath)), CSVFormat.DEFAULT
            .withHeader("collection_id", "collection_name", "permissions_read", "permissions_write", "rules",
                "name", "age", "date_created", "date_updated"
            ));

         val appWriteclient = Client()
             .setEndpoint(System.getenv("APPWRITE_ENDPOINT"))
             .setProject(projectId)
             .setKey(System.getenv("APPWRITE_API_KEY"))

         val s3 = AmazonS3Client(BasicAWSCredentials(
             System.getenv("AWS_API_KEY"),
             System.getenv("AWS_API_SECRET"))).apply {
             setEndpoint("s3.amazonaws.com").apply {
                 println("S3 endpoint is s3.amazonaws.com")
             }
             setS3ClientOptions(
                 S3ClientOptions.builder()
                     .setPathStyleAccess(true).build()
             )
         }

        val s3Bucket = System.getenv("BUCKET_NAME")
    }

    fun backupCollectionsToS3() {
        try {
            val collections = getAllCollections()

            for (collection in collections) {
                val documentList = getAllDocuments(collection.`$id`)
                for (document in documentList) {
                    val documentData = runBlocking {
                        Database(appWriteclient)
                            .getDocument(collectionId = collection.`$id`, documentId = document.`$id`).body?.string()
                    }
                    val documentResponseData = Gson().fromJson(documentData, Document::class.java) ?: throw Exception("Empty response")

                    writeToCsv(collection.`$id`, collection.name, collection.`$permissions`.read,
                        collection.`$permissions`.write, getRules(collection.rules), documentResponseData.name,
                        documentResponseData.age, collection.dateCreated, collection.dateUpdated
                    )
                }
            }
            csvPrinter.flush()
            csvPrinter.close()

            uploadToS3()
        } catch (e: Exception) {
            System.err.println(e.toString())
        } finally {
            exitProcess(0)
        }
    }

    private fun uploadToS3() {
        val objectMetadata = ObjectMetadata()
        objectMetadata.contentType = "text/csv"
        val s3response = s3.putObject(s3Bucket, "collections_$projectId", File(filePath).inputStream(), objectMetadata)
        if (s3response != null) {
            println("Successfully uploaded collections.csv to S3")
        }
    }

    private fun writeToCsv(
        collectionId: String, collectionName: String, readPermissions: List<String>,
        writePermissions: List<String>, rules: List<String>, name: String?, age: String?,
        createdAt: Long, updatedAt: Long
    ) {
        csvPrinter.printRecord(collectionId, collectionName, readPermissions.toString(), writePermissions.toString(),
            rules.toString(), name ?: "", age ?: "", createdAt.toString(), updatedAt.toString()
        )
    }

    private fun getAllDocuments(collectionId: String): MutableList<Document> {
        val documentList = mutableListOf<Document>()
        val limit = 100
        val response = getDocumentsWithOffset(collectionId, limit) ?: throw Exception("Empty response")

        documentList += response.documents

        val sum = response.sum
        val pages = (sum/limit)

        if(pages > 0) {
            val page = 1
            while (page < pages) {
                val response = getDocumentsWithOffset(collectionId, limit, page) ?: throw Exception("Empty response")

                documentList += response.documents
            }
        }

        return documentList
    }

    private fun getDocumentsWithOffset(collectionId: String, limit: Int, page: Int? = null): Documents? {
        val response = runBlocking {
            if (page != null) {
                Database(appWriteclient).listDocuments(collectionId, limit = limit, offset= (page * limit)).body?.string()
            } else {
                Database(appWriteclient).listDocuments(collectionId, limit = limit).body?.string()
            }
        }

        return Gson().fromJson(response, Documents::class.java)
    }

    private fun getAllCollections(): MutableList<Collection> {
        val collectionList = mutableListOf<Collection>()
        val limit = 100
        val response = getCollectionsWithOffset(limit) ?: throw Exception("Empty response")

        collectionList += response.collections

        val sum = response.sum
        val pages = (sum/limit)

        if(pages > 0) {
            val page = 1
            while (page < pages) {
                val response = getCollectionsWithOffset(limit, page) ?: throw Exception("Empty response")

                collectionList += response.collections
            }
        }

        return collectionList
    }

    private fun getCollectionsWithOffset(limit: Int, page: Int? = null): Collections? {
        val response = runBlocking {
            if (page != null) {
                Database(appWriteclient).listCollections(limit = limit, offset= (page * limit)).body?.string()
            } else {
                Database(appWriteclient).listCollections(limit = limit).body?.string()
            }
        }

        return Gson().fromJson(response, Collections::class.java)
    }

    private fun getRules(rules: List<Rule>): MutableList<String> {
        val rulesData = mutableListOf<String>()
        for (rule in rules) {
            rulesData.add(rule.`$id` + " " + rule.type + " " + rule.key + " " + rule.default)
        }

        return rulesData
    }
}

object BackupCollectionsToS3 {
    @JvmStatic
    fun main(args: Array<String>) {
        val backupToS3 = BackupToS3()
        backupToS3.backupCollectionsToS3()
    }

}