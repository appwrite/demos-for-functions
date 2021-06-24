
data class Permissions(val read: List<String>, val write: List<String>)

data class File(
    val `$id`: String,
    val name: String,
    val `$permissions`: Permissions,
    val dateCreated: Int,
    val signature: String,
    val mimeType: String,
    val sizeOriginal: Int
    )

data class FileList(val sum: Int, val files: List<File>?)