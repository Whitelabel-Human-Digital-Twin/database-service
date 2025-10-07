package io.github.whdt.db

import io.ktor.server.application.Application
import org.jetbrains.exposed.v1.jdbc.Database

fun Application.configureDatabases() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/HumanDigitalTwin",
        user = "postgres",
        password = "Macca03"
    )
}