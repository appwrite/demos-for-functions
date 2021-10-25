import com.google.gson.annotations.SerializedName

data class FunctionInput(val file: String?, @SerializedName("output_format") val outputFormat: String?)

data class Permissions(val read: List<String>, val write: List<String>)

data class File(
    val `$id`: String,
    val `$permissions`: Permissions,
    val name: String,
    val dateCreated: Int,
    val signature: String,
    val mimeType: String,
    val sizeOriginal: String
)
