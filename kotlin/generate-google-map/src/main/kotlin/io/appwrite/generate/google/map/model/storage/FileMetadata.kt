package io.appwrite.generate.google.map.model.storage

import kotlinx.serialization.Serializable

@Serializable
data class FileMetadata(
    val `$id`: String,
    val `$permissions`: Permissions,
    val name: String,
    val dateCreated: Int,
    val signature: String,
    val mimeType: String,
    val sizeOriginal: Int,
)
