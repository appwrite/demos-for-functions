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
//                val appWriteclient = Client()
//                    .setEndpoint(System.getenv("APPWRITE_ENDPOINT"))
//                    .setProject(System.getenv("APPWRITE_FUNCTION_PROJECT_ID"))
//                    .setKey(System.getenv("APPWRITE_API_KEY"))

                val appWriteclient = Client()
                    .setEndpoint("http://localhost:90/v1")
                    .setProject("6162ef5ab6c11")
                    .setKey("5b3a9d2401f6648ca3535a2d89be5dd941758479f29e515ed4958978b232b5f2b78af430eab443dcfd58db90c1c134e6b97f626795f6de346923c513b392cd7b23b0b4edbcafd1522055cae7670ffe1bfe97cbc6a235aeaf7a829b7d93acc1ceb293c5d753393060ed2e4f0778d6901edde97fa0b326beb91e9159d25c49afd5")

//                val s3 = AmazonS3Client(BasicAWSCredentials(
//                    System.getenv("AWS_API_KEY"),
//                    System.getenv("AWS_API_SECRET"))).apply {
//                        setEndpoint("s3.amazonaws.com").apply {
//                            println("S3 endpoint is s3.amazonaws.com")
//                        }
//                        setS3ClientOptions(
//                            S3ClientOptions.builder()
//                                .setPathStyleAccess(true).build()
//                        )
//                    }
                val s3 = AmazonS3Client(BasicAWSCredentials(
                    "AKIAUZ7FHRINECJWRA3Y",
                    "kZpa51dGsa4BVo6W0+18LscmM5MRl/q2oGmhxgLj")
                ).apply {
                    setEndpoint("s3.amazonaws.com")
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
