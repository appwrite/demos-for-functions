package io.appwrite.generate.google.map

import io.appwrite.Client
import io.appwrite.generate.google.map.model.AppwriteFile
import io.appwrite.generate.google.map.model.FunctionData
import io.appwrite.generate.google.map.model.storage.FileMetadata
import io.appwrite.generate.google.map.util.Env
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
            .setEndpoint(Env.getMandatory(ENV_APPWRITE_ENDPOINT))
            .setProject(Env.getMandatory(ENV_APPWRITE_PROJECT))
            .setKey(Env.getMandatory(ENV_APPWRITE_KEY))
            .setSelfSigned(true)
        val storage = Storage(client)

        val googleMaps = GoogleMaps()

        val functionData = System.getenv(ENV_APPWRITE_FUNCTION_DATA)
            ?.let { jsonParser.decodeFromString<FunctionData>(it) }
            ?: throw IllegalArgumentException("Function Data is required")
        println(functionData)

        val mapImage = googleMaps.generateMapImage(functionData)
        val file = AppwriteFile(functionData.outputFileName, mapImage.inputStream())
        println(storage.uploadFile(file))
    }

    @OptIn(ExperimentalSerializationApi::class) // required to use kotlinx.serialization's Json#decodeFromStream(InputStream)
    private fun Storage.uploadFile(file: AppwriteFile): FileMetadata = runBlocking {
        val createFileResponse = createFile(file.toFile())
        createFileResponse.body
            ?.let { jsonParser.decodeFromStream<FileMetadata>(it.byteStream()) }
            ?: throw RuntimeException("Error creating file ${file.name} in Appwrite: ${createFileResponse.message}")
    }
}
