package io.appwrite.merge.cloud.convert.files.model

import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

data class AppwriteFile(
    val name: String,
    val stream: InputStream,
) {
    fun toFile(): File {
        val file = File(name)
        val downloadFileOutputStream = FileOutputStream(file)
        stream.copyTo(downloadFileOutputStream)
        return file
    }
}
