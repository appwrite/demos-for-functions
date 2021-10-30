package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppwriteFile(
    @SerialName("\$id") val id : String,
    val name: String
)