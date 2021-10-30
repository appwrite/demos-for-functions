@file:UseSerializers(ProfileSerializer::class)

package io.appwrite.optimize.cloud.convert.file.model

import com.cloudconvert.dto.request.OptimizeFilesTaskRequest
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class FunctionData(
    val fileId: String,
    val inputFormat: String? = null,
    val engine: String? = null,
    val engineVersion: String? = null,
    val outputFileName: String? = null,
    val profile: OptimizeFilesTaskRequest.Profile? = null,
)

/**
 * Deserialize [OptimizeFilesTaskRequest.Profile] using [label][OptimizeFilesTaskRequest.Profile.label].
 */
object ProfileSerializer : KSerializer<OptimizeFilesTaskRequest.Profile> {
    private val className = OptimizeFilesTaskRequest.Profile::class.simpleName!!
    private val labelsToProfiles = OptimizeFilesTaskRequest.Profile.values().associateBy { it.label }

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        className, PrimitiveKind.STRING
    )

    override fun serialize(encoder: Encoder, value: OptimizeFilesTaskRequest.Profile) {
        encoder.encodeString(value.label)
    }

    override fun deserialize(decoder: Decoder): OptimizeFilesTaskRequest.Profile {
        val label = decoder.decodeString()
        return labelsToProfiles[label]
            ?: throw SerializationException("$label is not among valid $className enum values")
    }
}
