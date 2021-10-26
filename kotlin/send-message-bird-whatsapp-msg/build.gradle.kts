plugins {
    kotlin("jvm") version "1.5.31"
    kotlin("plugin.serialization") version "1.5.31"
    application
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "io.appwrite"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.messagebird:messagebird-api:3.1.4")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
}

application {
    mainClass.set("io.appwrite.send.message.bird.whatsapp.msg.ApplicationKt")
}

tasks.withType<Tar>() {
    compression = Compression.GZIP
    archiveExtension.set("tar.gz")
}

tasks {
    shadowJar {
        minimize {
            // required by com.essagebird:messagebird-api
            exclude(dependency("com.fasterxml.jackson.*:.*"))
        }
    }
}
