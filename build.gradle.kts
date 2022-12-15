plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "net.analyse"
version = "1.0.0"

repositories {
    mavenLocal()
    maven("https://jitpack.io/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://repo.maven.apache.org/maven2/")

    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.papermc.io/repository/maven-public/")

    mavenCentral()
}

dependencies {
    // Configuration file dependencies.
    implementation("ninja.leaping.configurate:configurate-yaml:3.7.1")

    // HTTP request dependencies.
    compileOnly("com.google.code.gson:gson:2.10")
    compileOnly("org.jetbrains:annotations:23.0.0")

    // Bukkit Platform
    compileOnly("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")

    // Bungee Platform
    compileOnly("net.md-5:bungeecord-api:1.18-R0.1-SNAPSHOT")

    // Velocity Platform
    compileOnly("com.velocitypowered:velocity-api:3.1.1")
    annotationProcessor("com.velocitypowered:velocity-api:3.1.1")
}

tasks {
    shadowJar {
        archiveFileName.set("${project.name}-${project.version}.jar")
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