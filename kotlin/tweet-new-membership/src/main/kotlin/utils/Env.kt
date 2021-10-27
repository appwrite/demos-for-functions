package utils

import kotlin.system.exitProcess


object Env {
    fun readString(key: String): String = try {
        System.getenv(key)!!
    } catch (e: NullPointerException) {
        System.err.println("[ERR] [$key] not found in environment variables")
        exitProcess(-1)
    }

    fun readString(key: String, defaultValue: String): String = try {
        System.getenv(key)!!
    } catch (e: NullPointerException) {
        println("[WARN] [$key] not found in environment variables, using [$defaultValue]")
        defaultValue
    }

    fun <T> read(key: String, parser: (String) -> T): T = try {
        parser(System.getenv(key)!!)
    } catch (e: NullPointerException) {
        System.err.println("[ERR] [$key] not found in environment variables")
        exitProcess(-1)
    }

    fun <T> read(key: String, defaultValue: T, parser: (String) -> T): T = try {
        parser(System.getenv(key)!!)
    } catch (e: NullPointerException) {
        println("[WARN] [$key] not found in environment variables, using [$defaultValue]")
        defaultValue
    }
}