@file:UseSerializers(TagSerializer::class)

package io.appwrite.send.message.bird.whatsapp.msg.model

import com.messagebird.objects.conversations.ConversationMessageTag
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonObject

@Serializable
data class FunctionData(
    val phoneNumber: String,
    val text: String,
    val source: JsonObject? = null,
    var tag: ConversationMessageTag? = null,
)

/**
 * Deserialize [ConversationMessageTag] using [tag][ConversationMessageTag.tag].
 */
object TagSerializer : KSerializer<ConversationMessageTag> {
    private val tagsToEnums = ConversationMessageTag.values()
        .associateBy { it.tag }

    override val descriptor = PrimitiveSerialDescriptor(
        ConversationMessageTag::class.simpleName!!, PrimitiveKind.STRING
    )

    override fun serialize(encoder: Encoder, value: ConversationMessageTag) {
        encoder.encodeString(value.tag)
    }

    override fun deserialize(decoder: Decoder): ConversationMessageTag {
        val tag = decoder.decodeString()
        return tagsToEnums[tag]
            ?: throw SerializationException("${FunctionData::tag.name} should be one of ${tagsToEnums.keys}")
    }
}
