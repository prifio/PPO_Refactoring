import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java`
    kotlin("jvm") version "1.5.31"
}

group = "me.prifio_k"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.eclipse.jetty:jetty-server:9.4.21.v20190926")
    implementation("org.eclipse.jetty:jetty-servlet:9.4.21.v20190926")
    implementation("org.xerial:sqlite-jdbc:3.8.11.2")
}

sourceSets {
    main {
        java {
            srcDir("src/main/kotlin")
        }
    }
}


tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}