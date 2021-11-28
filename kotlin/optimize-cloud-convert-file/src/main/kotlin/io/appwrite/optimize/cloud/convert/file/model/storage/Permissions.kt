package io.appwrite.optimize.cloud.convert.file.model.storage

import kotlinx.serialization.Serializable

@Serializable
data class Permissions(
    val read: Collection<String>,
    val write: Collection<String>,
)
