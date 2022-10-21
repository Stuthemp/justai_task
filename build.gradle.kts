
plugins {
    application
    kotlin("jvm") version "1.4.21"
    id("com.justai.jaicf.jaicp-build-plugin") version "0.1.1"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
    id("org.springframework.boot") version "2.6.13"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"

}

group = "com.justai.jaicf"
version = "1.0.0"

val jaicf = "1.2.2"
val logback = "1.2.3"

// Main class to run application on heroku. Either JaicpPollerKt, or JaicpServerKt. Will propagate to .jar main class.
application {
    mainClassName = "com.justai.jaicf.template.connections.JaicpServerKt"
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
    maven("https://jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    runtimeOnly("com.h2database:h2")

    implementation("ch.qos.logback:logback-classic:$logback")

    implementation("com.just-ai.jaicf:core:$jaicf")
    implementation("com.just-ai.jaicf:jaicp:$jaicf")
    implementation("com.just-ai.jaicf:caila:$jaicf")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    shadowJar {
        archiveFileName.set("app.jar")
    }
}

tasks.create("stage") {
    dependsOn("shadowJar")
}

tasks.withType<com.justai.jaicf.plugins.jaicp.build.JaicpBuild> {
    mainClassName.set(application.mainClassName)
}
