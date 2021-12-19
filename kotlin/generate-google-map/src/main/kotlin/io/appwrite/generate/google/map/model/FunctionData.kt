@file:UseSerializers(
    ImageFormatSerializer::class,
    MapTypeSerializer::class,
    MarkerSizeSerializer::class,
    MarkerCustomIconAnchorPointSerializer::class,
)

package io.appwrite.generate.google.map.model

import com.google.maps.StaticMapsRequest
import com.google.maps.internal.StringJoin
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*
import kotlin.reflect.KClass

@Serializable
data class FunctionData(
    val outputFileName: String,
    val width: Int,
    val height: Int,
    val center: Coordinates,
    val zoom: Int? = null,
    val scale: Int? = null,
    val format: StaticMapsRequest.ImageFormat? = null,
    val mapType: StaticMapsRequest.StaticMapType? = null,
    val region: String? = null,
    val markers: Collection<Marker> = emptyList(),
    val paths: Collection<Path> = emptyList(),
    val viewport: Coordinates? = null,
)

@Serializable
data class Coordinates(
    val latitude: Double,
    val longitude: Double,
)

@Serializable
data class Marker(
    val size: StaticMapsRequest.Markers.MarkersSize? = null,
    val color: String? = null,
    val label: Char? = null,
    val customIcon: MarkerCustomIcon? = null,
    val locations: Collection<Coordinates> = emptyList()
)

@Serializable
data class MarkerCustomIcon(
    val url: String,
    val anchorPoint: StaticMapsRequest.Markers.CustomIconAnchor,
    val scale: Int? = null,
)

@Serializable
data class Path(
    val weight: Int? = null,
    val color: String? = null,
    val fillColor: String? = null,
    val geodesic: Boolean? = null,
    val points: Collection<Coordinates> = emptyList()
)

object ImageFormatSerializer : UrlValueEnumSerializer<StaticMapsRequest.ImageFormat>(
    FunctionData::format.name,
    StaticMapsRequest.ImageFormat::class,
)

object MapTypeSerializer : UrlValueEnumSerializer<StaticMapsRequest.StaticMapType>(
    FunctionData::mapType.name,
    StaticMapsRequest.StaticMapType::class,
)

object MarkerSizeSerializer : UrlValueEnumSerializer<StaticMapsRequest.Markers.MarkersSize>(
    "${FunctionData::markers.name}.${Marker::size.name}",
    StaticMapsRequest.Markers.MarkersSize::class,
)

object MarkerCustomIconAnchorPointSerializer : UrlValueEnumSerializer<StaticMapsRequest.Markers.CustomIconAnchor>(
    "${FunctionData::markers.name}.${Marker::customIcon.name}.${MarkerCustomIcon::anchorPoint.name}",
    StaticMapsRequest.Markers.CustomIconAnchor::class,
)

sealed class UrlValueEnumSerializer<T>(
    private val parameterName: String,
    enumClass: KClass<T>,
) : KSerializer<T> where T : Enum<T>, T : StringJoin.UrlValue {
    private val urlValuesToEnums = EnumSet.allOf(enumClass.java)
        .associateBy { it.toUrlValue() }

    override val descriptor = PrimitiveSerialDescriptor(enumClass.simpleName!!, PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: T) {
        encoder.encodeString(value.toUrlValue())
    }

    override fun deserialize(decoder: Decoder): T {
        val urlValue = decoder.decodeString()
        return urlValuesToEnums[urlValue]
            ?: throw SerializationException("$parameterName should be one of ${urlValuesToEnums.keys}")
    }
}
