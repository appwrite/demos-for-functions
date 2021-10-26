import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
    implementation("io.appwrite:sdk-for-kotlin:0.1.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
    implementation("com.cloudconvert:cloudconvert-java:1.0.7")
    implementation("org.slf4j:slf4j-simple:1.7.32")
}

tasks.withType<KotlinCompile>().configureEach {
    // required to use kotlinx.serialization's Json#decodeFromStream(InputStream)
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}

application {
    mainClass.set("io.appwrite.merge.cloud.convert.files.ApplicationKt")
}

tasks.withType<Tar>() {
    compression = Compression.GZIP
    archiveExtension.set("tar.gz")
}

tasks {
    shadowJar {
        minimize {
            // required by com.cloudconvert:cloudconvert-java
            exclude(dependency("commons-logging:commons-logging"))
            exclude(dependency("com.fasterxml.jackson.*:.*"))
        }
    }
}
