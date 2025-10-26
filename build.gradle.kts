plugins {
    java
    alias(libs.plugins.lavalink)
    kotlin("jvm")
    id("maven-publish")
}

group = "org.ferrymehdi"
version = "1.0.0"

lavalinkPlugin {
    name = "lava-deezer-mirror"
    apiVersion = libs.versions.lavalink.api
    serverVersion = libs.versions.lavalink.server
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }
}

dependencies {
    compileOnly("dev.arbjerg:lavaplayer:2.1.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation("org.jetbrains.kotlin:kotlin-annotations-jvm:1.9.0")
    implementation("com.auth0:java-jwt:4.4.0")
    implementation("com.github.topi314.lavasrc:lavasrc-protocol:4.6.0")
    implementation ("com.github.topi314.lavasrc:lavasrc:4.6.0")
    compileOnly("org.slf4j:slf4j-api:2.0.7")
    implementation(kotlin("stdlib-jdk8"))
}
repositories {
    mavenCentral()

    maven {url = uri("https://maven.topi.wtf/releases")}
    maven (url = "https://maven.lavalink.dev/releases")
}


publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = "org.ferrymehdi"
            artifactId = "lava-deezer-mirror"
            version = "1.0.0"
        }
    }
    repositories {
        mavenLocal()
    }
}
