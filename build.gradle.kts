import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "dev.kry"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()

    maven("https://jitpack.io")
}

dependencies {
    compileOnly(fileTree(mapOf("dir" to "lib", "include" to listOf("*.jar"))))

    implementation("org.mongodb:mongo-java-driver:3.12.11")
    implementation("org.projectlombok:lombok:1.18.24")
    implementation("com.github.azbh111:ProtocolLib:4.4.0")
    implementation("com.google.code.gson:gson:2.10")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}