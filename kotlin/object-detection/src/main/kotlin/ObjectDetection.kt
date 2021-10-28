import io.appwrite.Client
import io.appwrite.services.Storage
import com.cloudmersive.client.invoker.ApiClient
import com.cloudmersive.client.invoker.ApiException
import com.cloudmersive.client.invoker.Configuration
import com.cloudmersive.client.invoker.auth.*
import com.cloudmersive.client.RecognizeApi
import com.cloudmersive.client.model.ObjectDetectionResult
import com.google.gson.JsonParser
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

suspend fun main(args: Array<String>) {
    val client = Client()
        .setEndpoint(System.getenv("APPWRITE_ENDPOINT"))
        .setProject(System.getenv("APPWRITE_FUNCTION_PROJECT_ID"))
        .setKey(System.getenv("APPWRITE_API_KEY"))
    val storage = Storage(client)

    val defaultClient: ApiClient = Configuration.getDefaultApiClient()

    val Apikey: ApiKeyAuth = defaultClient.getAuthentication("Apikey") as ApiKeyAuth
    Apikey.setApiKey(System.getenv("CLOUDMERSIVE_API_KEY"))

    val apiInstance = RecognizeApi()

    val payload = System.getenv("APPWRITE_FUNCTION_EVENT_DATA")

    if (payload != null && payload.isNotEmpty()) {
        try {
            val json = JsonParser().parse(payload).asJsonObject;
            val fileId = json.get("\$id").asString
            val result = storage.getFilePreview(fileId)
            val imageFile = File("yourFile.png")
            val inStream: InputStream = result.body!!.byteStream()
            val fos = FileOutputStream(imageFile)

            var length = -1
            val buffer = ByteArray(1024) // buffer for portion of data from connection

            while (inStream.read(buffer).also { length = it } > -1) {
                fos.write(buffer, 0, length)
            }
            fos.close()
            inStream.close()
            try {
                val result: ObjectDetectionResult = apiInstance.recognizeDetectObjects(imageFile)
                println(result)
            } catch (e: ApiException) {
                System.err.println("Exception when calling RecognizeApi#recognizeDetectObjects")
                e.printStackTrace()
            }
        } catch (e: Exception) {
            println("[ERROR] There was an error")
            println(e.message)
        }
    } else {
        println("[INFO] APPWRITE_FUNCTION_EVENT_DATA is empty")
    }
}