package io.github.whdt.db

import io.ktor.server.application.Application
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase

fun Application.configureDatabases() {
    R2dbcDatabase.connect(
        url = "${System.getenv("DATABASE_URL_BASE")}/${System.getenv("DATABASE_NAME")}",
        driver = "postgresql",
        user = System.getenv("DATABASE_USERNAME"),
        password = System.getenv("DATABASE_PASSWORD")
    )
}