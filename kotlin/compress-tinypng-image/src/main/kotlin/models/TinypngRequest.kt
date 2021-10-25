package models

import kotlinx.serialization.Serializable

@Serializable
data class TinypngRequest(
    val source: Source
) {
    constructor(imageUrl: String) : this(
        Source(imageUrl)
    )
}

@Serializable
data class Source(
    val url: String
)