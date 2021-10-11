data class EnvironmentVars(
    val appwriteApiEndpoint: String,
    val appwriteProjectId: String,
    val appwriteSecretKey: String,
    val fileId: String,
)

fun readEnvVars() = EnvironmentVars(
    appwriteApiEndpoint = getEnvVar("APPWRITE_ENDPOINT"),
    appwriteProjectId = getEnvVar("APPWRITE_FUNCTION_PROJECT_ID"),
    appwriteSecretKey = getEnvVar("APPWRITE_API_KEY"),
    fileId = getEnvVar("APPWRITE_FUNCTION_DATA")
)
