import com.cloudconvert.client.CloudConvertClient
import com.cloudconvert.client.setttings.StringSettingsProvider
import com.cloudconvert.dto.request.ConvertFilesTaskRequest
import com.cloudconvert.dto.request.UploadImportRequest
import com.cloudconvert.dto.request.UrlExportRequest
import com.google.gson.Gson
import io.appwrite.Client
import io.appwrite.services.Storage
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import java.io.File as JavaFile
import java.io.InputStream
import java.lang.NullPointerException
import kotlin.system.exitProcess

suspend fun main() {
    // Receive and validate user input
    val data: FunctionInput = Gson().fromJson(System.getenv("APPWRITE_FUNCTION_DATA"), FunctionInput::class.java)

    if (data.file == null || data.outputFormat == null) {
        System.err.println("File ID ('file') and output format ('output_format') are required function input, but not provided.")
        exitProcess(1)
    }

    if (!listOf("png", "jpg").contains(data.outputFormat.lowercase())) {
        System.err.println("Only allowed output types are PNG and JPG (you provided ${data.outputFormat})")
        exitProcess(1)
    }

    // Create clients
    val client = Client()
        .setEndpoint(System.getenv("APPWRITE_ENDPOINT"))
        .setProject(System.getenv("APPWRITE_FUNCTION_PROJECT_ID"))
        .setKey(System.getenv("APPWRITE_API_KEY"))
    val storage = Storage(client)

    val cloudConvertClient = CloudConvertClient(
        StringSettingsProvider(
            System.getenv("CLOUDCONVERT_API_KEY"),
            System.getenv("CLOUDCONVERT_WEBHOOK_SIGNING_SECRET"),
            false // Set to true for enabling Sandbox Mode
        )
    )

    // Create job for converting
    val job = cloudConvertClient.jobs().create(mapOf(
        "upload-file" to UploadImportRequest(),
        "convert-file" to ConvertFilesTaskRequest()
            .setInput("upload-file")
            .setOutputFormat(data.outputFormat.lowercase()),
        "export-file" to UrlExportRequest()
            .setInput("convert-file")
            .setInline(false)
            .setArchiveMultipleFiles(true)
    )).body!!

    // Fetch file information and bytestream
    val fileInfo: File
    val file: InputStream
    try {
        fileInfo = Gson().fromJson(storage.getFile(data.file).body?.string()!!, File::class.java)
        file = storage.getFileView(data.file).body?.byteStream()!!
    } catch (e: NullPointerException) {
        System.err.println("Unable to find file ${data.file}")
        throw e
    }

    // Upload file
    val uploadTask = job
        .tasks
        .stream()
        .filter { it.name == "upload-file" }.findFirst()
        .get()
    uploadTask.runCatching {
        cloudConvertClient.importUsing().upload(id!!, result.form!!, file, fileInfo.name)
    }.getOrElse {
        System.err.println("Failed to upload file ${fileInfo.name} to CloudConvert")
        throw it
    }

    val convertedFile = cloudConvertClient
        .jobs()
        .wait(job.id)
        .body!!
        .tasks
        .stream()
        .filter { it.name == "export-file" }.findFirst()
        .get()
        .result
        .files[0]

    val newFile = JavaFile(convertedFile["filename"]!!)

    HttpClient().use {
        val httpResponse: HttpResponse = it.get(convertedFile["url"]!!)
        val responseBody: ByteArray = httpResponse.receive()
        newFile.writeBytes(responseBody)
    }

    val newAppwriteFile = storage.createFile(newFile).body?.string()!!.run {
        Gson().fromJson(this, File::class.java)
    }

    newFile.deleteOnExit()

    println("Successfully converted file ${fileInfo.`$id`} to ${newAppwriteFile.`$id`} (format ${data.outputFormat})")
    exitProcess(0)
}
