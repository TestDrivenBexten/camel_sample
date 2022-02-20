import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0"
    application
}

group = "bexten.camel_sample"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("org.apache.camel:camel-core:3.14.0")
    implementation("org.apache.camel:camel-jackson:3.14.0")
    implementation("org.apache.camel:camel-main:3.14.0")
    implementation("org.apache.camel:camel-netty-http:3.14.0")
    implementation("org.apache.camel:camel-rest:3.14.0")
    implementation("org.apache.camel:camel-stream:3.14.0")
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "16"
}

application {
    mainClass.set("MainAppKt") 
}
