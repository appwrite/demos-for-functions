package io.appwrite.optimize.cloud.convert.file

import io.appwrite.Client
import io.appwrite.optimize.cloud.convert.file.model.AppwriteFile
import io.appwrite.optimize.cloud.convert.file.model.FunctionData
import io.appwrite.optimize.cloud.convert.file.model.storage.FileMetadata
import io.appwrite.services.Storage
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlin.system.exitProcess

fun main() {
    try {
        Application.run()
    } catch (exception: Exception) {
        exception.printStackTrace()
        exitProcess(1)
    }
    exitProcess(0)
}

object Application {
    private const val ENV_APPWRITE_ENDPOINT = "APPWRITE_ENDPOINT"
    private const val ENV_APPWRITE_PROJECT = "APPWRITE_FUNCTION_PROJECT_ID"
    private const val ENV_APPWRITE_KEY = "APPWRITE_API_KEY"
    private const val ENV_APPWRITE_FUNCTION_DATA = "APPWRITE_FUNCTION_DATA"

    private val jsonParser = Json { isLenient = true }

    fun run() {
        val client = Client()
            .setEndpoint(getMandatoryEnv(ENV_APPWRITE_ENDPOINT))
            .setProject(getMandatoryEnv(ENV_APPWRITE_PROJECT))
            .setKey(getMandatoryEnv(ENV_APPWRITE_KEY))
        val storage = Storage(client)

        val functionData = System.getenv(ENV_APPWRITE_FUNCTION_DATA)
            ?.let { jsonParser.decodeFromString<FunctionData>(it) }
            ?: throw IllegalArgumentException("Function Data is required")
        println(functionData)

        val cloudConvert = CloudConvert()

        val file = runBlocking {
            storage.downloadFile(functionData.fileId)
        }

        val optimizedFile = cloudConvert.optimize(functionData, file)
        println(storage.uploadFile(optimizedFile))
    }

    private fun getMandatoryEnv(env: String): String {
        return System.getenv(env)
            ?: throw RuntimeException("Environment Variable not configured: $env")
    }

    @OptIn(ExperimentalSerializationApi::class) // required to use kotlinx.serialization's Json#decodeFromStream(InputStream)
    private suspend fun Storage.downloadFile(fileId: String): AppwriteFile {
        val fileMetadataResponse = getFile(fileId)
        val fileMetadata = fileMetadataResponse.body?.byteStream()
            ?.let { jsonParser.decodeFromStream<FileMetadata>(it) }
            ?: throw RuntimeException("Error getting metadata for file in Appwrite Storage with ID $fileId: ${fileMetadataResponse.message}")
        val fileDownloadResponse = getFileDownload(fileId)
        val fileStream = fileDownloadResponse.body?.byteStream()
            ?: throw RuntimeException("Error downloading file in Appwrite Storage with ID $fileId: ${fileDownloadResponse.message}")
        return AppwriteFile(fileMetadata.name, fileStream)
    }

    @OptIn(ExperimentalSerializationApi::class) // required to use kotlinx.serialization's Json#decodeFromStream(InputStream)
    private fun Storage.uploadFile(file: AppwriteFile): FileMetadata = runBlocking {
        val createFileResponse = createFile(file.toFile())
        createFileResponse.body
            ?.let { jsonParser.decodeFromStream<FileMetadata>(it.byteStream()) }
            ?: throw RuntimeException("Error creating file ${file.name} in Appwrite: ${createFileResponse.message}")
    }
}
