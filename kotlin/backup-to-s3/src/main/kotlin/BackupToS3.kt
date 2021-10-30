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
        private const val headers = "collection_id, permissions, name, date_created, date_updated, rules"

        @JvmStatic
        fun main(args: Array<String>) {
            try {
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
                
                collectionsCsv = FileWriter("${System.getProperty("user.dir")}/collections.csv")
                if (collectionsCsv == null) {
                    throw Exception("Error in creating file")
                }

                collectionsCsv!!.append(headers)
                collectionsCsv!!.append('\n')

                val database = Database(appWriteclient)
                val response = runBlocking {
                    database.listCollections().body?.string()
                }

                val responseBody = Gson().fromJson(response, Collections::class.java) ?: throw Exception("Empty response")

                for (collection in responseBody.collections) {
                    collectionsCsv!!.append(collection.id)
                    collectionsCsv!!.append(",")
                    if (collection.permissions != null) {
                        collectionsCsv!!.append(collection.permissions.toString())
                    } else {
                        collectionsCsv!!.append("")
                    }
                    collectionsCsv!!.append(",")
                    collectionsCsv!!.append(collection.name)
                    collectionsCsv!!.append(",")
                    collectionsCsv!!.append(collection.dateCreated.toString())
                    collectionsCsv!!.append(",")
                    collectionsCsv!!.append(collection.dateUpdated.toString())
                    collectionsCsv!!.append(",")
                    collectionsCsv!!.append(collection.rules.toString())
                    collectionsCsv!!.append("\n")
                }

                val file = File("${System.getProperty("user.dir")}/collections.csv")

                val s3response = s3.putObject(PutObjectRequest(
                    "awhacktoberfest",
                    "collections",
                    file
                ))

                if (s3response != null) {
                    println("Successfully uploaded collections.csv to S3")
                }
            } catch (e: Exception) {
                println(e)
            } finally {
                if (collectionsCsv != null) {
                    collectionsCsv!!.flush()
                }
                exitProcess(0)
            }
        }
    }
}
