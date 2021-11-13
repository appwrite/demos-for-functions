import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.S3ClientOptions
import com.amazonaws.services.s3.model.PutObjectRequest
import com.google.gson.Gson
import io.appwrite.Client
import io.appwrite.services.Database
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileWriter
import kotlin.system.exitProcess


class BackupToS3 {
    companion object {
        private var collectionsCsv: FileWriter? = null
        private const val headers = "collection_id, collection_name, permissions_read, permissions_write, rules, name, age, date_created, date_updated"
        val appWriteclient = Client()
            .setEndpoint(System.getenv("APPWRITE_ENDPOINT"))
            .setProject(System.getenv("APPWRITE_FUNCTION_PROJECT_ID"))
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
    }

    fun backupCollectionsToS3() {
        try {
            collectionsCsv = FileWriter("${System.getProperty("user.dir")}/collections.csv")
            if (collectionsCsv == null) {
                throw Exception("Error in creating file")
            }

            collectionsCsv!!.append(headers)
            collectionsCsv!!.append('\n')

            val collections = getCollections()

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

            uploadToS3()
        } catch (e: Exception) {
            println(e)
        } finally {
            if (collectionsCsv != null) {
                collectionsCsv!!.flush()
            }
            exitProcess(0)
        }
    }

    private fun uploadToS3() {
        val file = File("${System.getProperty("user.dir")}/collections.csv")

        val s3response = s3.putObject(PutObjectRequest(System.getenv("BUCKET_NAME"), "collections", file))

        if (s3response != null) {
            println("Successfully uploaded collections.csv to S3")
        }
    }

    private fun writeToCsv(
        collectionId: String, collectionName: String, readPermissions: List<String>,
        writePermissions: List<String>, rules: List<String>, name: String?, age: String?,
        createdAt: Long, updatedAt: Long
    ) {
        collectionsCsv!!.append(collectionId)
        collectionsCsv!!.append(",")
        collectionsCsv!!.append(collectionName)
        collectionsCsv!!.append(",")
        collectionsCsv!!.append(readPermissions.toString())
        collectionsCsv!!.append(",")
        collectionsCsv!!.append(writePermissions.toString())
        collectionsCsv!!.append(",")
        collectionsCsv!!.append(rules.toString())
        collectionsCsv!!.append(",")
        collectionsCsv!!.append(name ?: "")
        collectionsCsv!!.append(",")
        collectionsCsv!!.append(age ?: "")
        collectionsCsv!!.append(",")
        collectionsCsv!!.append(createdAt.toString())
        collectionsCsv!!.append(",")
        collectionsCsv!!.append(updatedAt.toString())
        collectionsCsv!!.append("\n")
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
                val response = getDocumentsWithOffset(collectionId, limit) ?: throw Exception("Empty response")

                documentList += response.documents
            }
        }

        return documentList
    }

    private fun getDocumentsWithOffset(collectionId: String, limit: Int, page: Int? = null): Documents? {
        val resultDocs = runBlocking {
            if (page != null) {
                Database(appWriteclient).listDocuments(collectionId, limit = limit, offset= (page * limit)).body?.string()
            } else {
                Database(appWriteclient).listDocuments(collectionId, limit = limit).body?.string()
            }
        }

        return Gson().fromJson(resultDocs, Documents::class.java)
    }

    private fun getCollections(): List<Collection> {
        val database = Database(appWriteclient)
        val response = runBlocking {
            database.listCollections().body?.string()
        }

        val responseBody = Gson().fromJson(response, Collections::class.java) ?: throw Exception("Empty response")

        return responseBody.collections
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