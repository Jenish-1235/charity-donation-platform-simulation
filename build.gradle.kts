plugins {
    kotlin("jvm") version "1.9.0"  // or your version
    application
    id("com.github.johnrengelman.shadow") version "7.1.2"
}
group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.mysql:mysql-connector-j:9.3.0")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("org.example.Main")
}


tasks.jar {
    manifest {
        attributes["Main-Class"] = "org.example.Main"
    }
}