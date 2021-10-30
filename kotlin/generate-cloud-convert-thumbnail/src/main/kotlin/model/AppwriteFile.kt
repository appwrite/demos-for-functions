package model

data class AppwriteFile(
    val `$id`: String,
    val name: String,
    val `$permissions`: AppwritePermissions,
    val dateCreated: Int,
    val signature: String,
    val mimeType: String,
    val sizeOriginal: Int
)
