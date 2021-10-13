plugins {
    kotlin("jvm") version "1.5.31"
}

group = "com.example"
version = "1.0-SNAPSHOT"

tasks.withType<Jar>() {
    manifest {
        attributes["Main-Class"] = "MainKt"
    }

    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get()
            .filter { it.name.endsWith("jar") }
            .map { zipTree(it) }
    })
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.appwrite:sdk-for-kotlin:0.1.0")
    implementation("com.google.code.gson:gson:2.8.8")
    implementation("com.cloudconvert:cloudconvert-java:1.0.7")
    implementation("io.ktor:ktor-client-core:1.6.4")
    implementation("io.ktor:ktor-client-cio:1.6.4")
}
