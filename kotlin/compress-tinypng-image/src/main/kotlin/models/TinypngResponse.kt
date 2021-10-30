package models

import kotlinx.serialization.Serializable

@Serializable
data class TinypngResponse(
    val output: TinypngOutput
)

@Serializable
data class TinypngOutput(
    val url: String
)