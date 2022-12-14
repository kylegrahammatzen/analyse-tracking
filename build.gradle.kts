plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "net.analyse"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://jitpack.io/")
    mavenCentral()
}

dependencies {
    compileOnly("com.google.code.gson:gson:2.10")
    compileOnly("org.jetbrains:annotations:23.0.0")
    compileOnly("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")
}

tasks {
    shadowJar {
        archiveFileName.set("${project.name}-analyse-${project.version}.jar")
    }
    compileJava {
        options.encoding = "UTF-8"
    }

    processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE

        filesNotMatching("**/*.zip") {
            expand("pluginVersion" to version)
        }
    }
}

