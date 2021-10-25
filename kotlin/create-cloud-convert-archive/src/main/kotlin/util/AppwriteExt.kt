package util

import io.appwrite.services.Storage
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import model.RawFile
import model.appwrite.AppwriteFile
import java.io.File

suspend fun Storage.getRawFile(fileId: String, jsonParser: Json): RawFile =
    runCatching {
        val fileStream = getFileView(fileId).body?.byteStream()!!
        val metadata = getFile(fileId).body?.string()!!
        val file: AppwriteFile = jsonParser.decodeFromString(metadata)

        RawFile(
            file.name,
            fileStream
        )
    }.getOrElse { e ->
        System.err.println("[ERR] Unable to read file with id: $fileId")
        throw(e)
    }

suspend fun Storage.createFromUrl(filename: String, url: String, jsonParser: Json): AppwriteFile {
    val file = File(filename)

    HttpClient().use { httpClient ->
        val httpResponse: HttpResponse = httpClient.get(url)
        val responseBody: ByteArray = httpResponse.receive()
        file.writeBytes(responseBody)
    }

    val appwriteFile: AppwriteFile = createFile(file).body?.string()!!.run {
        jsonParser.decodeFromString(this)
    }

    file.deleteOnExit()
    return appwriteFile
}