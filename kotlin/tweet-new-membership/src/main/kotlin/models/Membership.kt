package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Membership(
    @SerialName("name") val username: String,
    val teamId: String
) {
    val tweetText: String
        get() = """[THIS IS AN API TEST]
            |
            |Hey $username,
            |Welcome to our team: $teamId
            |
            |[THIS IS AN API TEST]
        """.trimMargin()
}
