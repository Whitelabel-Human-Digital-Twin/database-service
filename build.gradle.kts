val kotlin_version: String by project
val logback_version: String by project
val ktor_version: String by project

plugins {
    kotlin("jvm") version "2.2.20"
    id("io.ktor.plugin") version "3.3.0"
    kotlin("plugin.serialization") version "2.1.10"
}

group = "io.github.whdt"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

repositories {
    maven {
        url = uri("https://maven.pkg.github.com/Whitelabel-Human-Digital-Twin/whdt") // or the correct GitHub repo
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("GPR_USER")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("GPR_TOKEN")
        }
    }
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-cors")
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("io.ktor:ktor-server-netty")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-config-yaml")
    implementation("io.ktor:ktor-serialization-kotlinx-json:${ktor_version}")
    implementation("org.jetbrains.exposed:exposed-core:1.0.0-rc-1")
    implementation("org.jetbrains.exposed:exposed-r2dbc:1.0.0-rc-1")
    implementation("org.jetbrains.exposed:exposed-dao:1.0.0-rc-1")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:1.0.0-rc-1")
    implementation("org.jetbrains.exposed:exposed-java-time:1.0.0-rc-1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.postgresql:postgresql:42.7.7")
    implementation("org.postgresql:r2dbc-postgresql:1.0.7.RELEASE")
    testImplementation("io.kotest:kotest-runner-junit5:5.8.0")
    testImplementation("io.kotest:kotest-assertions-core:5.8.0")
    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    implementation("com.hivemq:hivemq-mqtt-client:1.3.0")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")

    implementation("io.github.whdt:whdt-core:0.3.0")
    implementation("io.github.whdt:whdt-distributed:0.1.0")
    implementation("io.github.whdt:whdt-wldt-plugin:0.3.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
