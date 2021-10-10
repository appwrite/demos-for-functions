plugins {
    java
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
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}
