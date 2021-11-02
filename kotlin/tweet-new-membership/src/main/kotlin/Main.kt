import io.github.redouane59.twitter.TwitterClient
import io.github.redouane59.twitter.dto.tweet.Tweet
import io.github.redouane59.twitter.signature.TwitterCredentials
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import models.Membership
import utils.Env
import kotlin.system.exitProcess

private const val APPWRITE_FUNCTION_EVENT_DATA = "APPWRITE_FUNCTION_EVENT_DATA"

private const val TWITTER_API_KEY = "TWITTER_API_KEY"
private const val TWITTER_API_KEY_SECRET = "TWITTER_API_KEY_SECRET"
private const val TWITTER_ACCESS_TOKEN = "TWITTER_ACCESS_TOKEN"
private const val TWITTER_ACCESS_TOKEN_SECRET = "TWITTER_ACCESS_TOKEN_SECRET"

fun main() {
    val jsonParser = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }

    val membership: Membership = Env.read(APPWRITE_FUNCTION_EVENT_DATA) { jsonParser.decodeFromString(it) }

    val tweet = tweetMembership(membership)
    println(tweet.id)

    exitProcess(0)
}

fun tweetMembership(membership: Membership): Tweet {
    val twitterClient = TwitterClient(
        TwitterCredentials.builder()
            .accessToken(Env.readString(TWITTER_ACCESS_TOKEN))
            .accessTokenSecret(Env.readString(TWITTER_ACCESS_TOKEN_SECRET))
            .apiKey(Env.readString(TWITTER_API_KEY))
            .apiSecretKey(Env.readString(TWITTER_API_KEY_SECRET))
            .build()
    )

    return twitterClient.postTweet(membership.tweetText)
}
