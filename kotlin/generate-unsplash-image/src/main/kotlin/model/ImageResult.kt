package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultWrapper(
    @SerialName("results") val results: List<ImageResult>
)

@Serializable
data class ImageResult(
    @SerialName("user") val user: User,
    @SerialName("links") val links: Map<String, String>
) {
    val imageUrl: String = links["download"]!!
    val imageAuthor: String = user.name
}

@Serializable
data class User(
    @SerialName("name") val name: String
)
