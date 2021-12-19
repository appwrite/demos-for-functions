package io.appwrite.generate.google.map.util

object Env {
    fun getMandatory(env: String): String {
        return System.getenv(env)
            ?: throw RuntimeException("Environment Variable not configured: $env")
    }
}
