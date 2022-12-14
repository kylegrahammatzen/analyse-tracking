plugins {
    id("java")
}

group = "net.analyse"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://jitpack.io/")
    maven("https://libraries.minecraft.net/")
    maven("https://repo.codemc.org/repository/maven-public/")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

jar {
    manifest {
        attributes(
                'Main-Class': 'net.analyse.base.Base'
        )
    }
}

task fatJar(type: Jar) {
    manifest.from jar.manifest
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from {
        // change here: runtimeClasspath instead of runtime
        configurations.runtimeClasspath.collect { it.isDirectory() ? it : zipTree(it) }
    } {
        exclude "META-INF/.SF"
        exclude "META-INF/.DSA"
        exclude "META-INF/*.RSA"
    }
    with jar
}