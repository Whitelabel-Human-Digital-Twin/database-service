package io.github.whdt.db

import io.github.cdimascio.dotenv.dotenv
import io.github.whdt.db.mappingEntities.*
import io.github.whdt.db.mappingRelations.*
import io.ktor.server.application.*
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.SchemaUtils
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction

fun Application.configureDatabases() {
    val dotenv = dotenv {
        ignoreIfMissing = true
    }
    val dbUrlBase = System.getenv("DATABASE_URL_BASE") ?: dotenv.get("DATABASE_URL_BASE")
    val dbName = System.getenv("DATABASE_NAME") ?: dotenv.get("DATABASE_NAME")
    val dbUser = System.getenv("DATABASE_USER") ?: dotenv.get("DATABASE_USER")
    val dbPassword = System.getenv("DATABASE_PASSWORD") ?: dotenv.get("DATABASE_PASSWORD")
    R2dbcDatabase.connect(
        url = "$dbUrlBase/$dbName",
        driver = "postgresql",
        user = dbUser,
        password = dbPassword
    )
}

suspend fun Application.configureDatabaseSchema() = suspendTransaction {
    // create entities
    SchemaUtils.create(
        HumanDigitalTwinTable,
        InterfaceTable,
        PropertyTable,
        TimeTable,
        ValueTable,
    )
    // create relations
    SchemaUtils.create(
        AssociatedTable,
        DefinesTable,
        ImplementsTable,
        InteractsTable,
        SamplingTable,
    )
}
