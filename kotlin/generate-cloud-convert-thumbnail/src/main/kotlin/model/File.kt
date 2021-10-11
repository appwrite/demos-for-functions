package model

data class File(
    val `$id`: String,
    val name: String,
    val `$permissions`: Permissions,
    val dateCreated: Int,
    val signature: String,
    val mimeType: String,
    val sizeOriginal: Int
)
