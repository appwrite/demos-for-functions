package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Gif(
   @SerialName("url") val url: String
)

@Serializable
data class Response(
    @SerialName("data") val gifs: List<Gif>
)