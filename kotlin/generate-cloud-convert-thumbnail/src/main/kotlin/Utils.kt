fun getEnvVar(key: String): String = try {
    System.getenv(key)!!
} catch (e: NullPointerException) {
    System.err.println("[ERR] $key not set in env")
    throw(e)
}