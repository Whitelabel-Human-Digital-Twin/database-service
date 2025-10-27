package io.github.whdt.db

import io.ktor.server.application.Application
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase

fun Application.configureDatabases() {
    R2dbcDatabase.connect(
        url = "r2dbc:postgresql://localhost:5432/HumanDigitalTwin",
        driver = "postgresql",
        user = "postgres",
        password = "Macca03"
    )
}