package io.appwrite.merge.cloud.convert.files.model

import kotlinx.serialization.Serializable

@Serializable
data class FunctionData(
    val fileIds: Collection<String>,
    val engine: String? = null,
    val engineVersion: String? = null,
    val outputFileName: String? = null,
)
