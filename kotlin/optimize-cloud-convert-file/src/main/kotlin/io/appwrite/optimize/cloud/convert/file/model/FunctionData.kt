@file:UseSerializers(ProfileSerializer::class)

package io.appwrite.optimize.cloud.convert.file.model

import com.cloudconvert.dto.request.OptimizeFilesTaskRequest
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
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
    private val labelsToProfiles = OptimizeFilesTaskRequest.Profile.values()
        .associateBy { it.label }

    override val descriptor = PrimitiveSerialDescriptor(
        OptimizeFilesTaskRequest.Profile::class.simpleName!!, PrimitiveKind.STRING
    )

    override fun serialize(encoder: Encoder, value: OptimizeFilesTaskRequest.Profile) {
        encoder.encodeString(value.label)
    }

    override fun deserialize(decoder: Decoder): OptimizeFilesTaskRequest.Profile {
        val label = decoder.decodeString()
        return labelsToProfiles[label]
            ?: throw SerializationException("${FunctionData::profile.name} should be one of ${labelsToProfiles.keys}")
    }
}
