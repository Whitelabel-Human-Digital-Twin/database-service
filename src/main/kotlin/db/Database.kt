package io.github.whdt.db

import io.github.cdimascio.dotenv.dotenv
import io.github.whdt.db.mappingEntities.*
import io.github.whdt.db.mappingRelations.*
import io.ktor.server.application.*
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.SchemaUtils
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction

val dotenv = dotenv {
    ignoreIfMissing = true
}

val dbUrlBase: String = System.getenv("DATABASE_URL_BASE") ?: dotenv.get("DATABASE_URL_BASE")
val dbName: String = System.getenv("DATABASE_NAME") ?: dotenv.get("DATABASE_NAME")
val dbUser: String = System.getenv("DATABASE_USER") ?: dotenv.get("DATABASE_USER")
val dbPassword: String = System.getenv("DATABASE_PASSWORD") ?: dotenv.get("DATABASE_PASSWORD")


fun Application.configureDatabases() {
   R2dbcDatabase.connect(
        url = "$dbUrlBase/$dbName",
        driver = "postgresql",
        user = dbUser,
        password = dbPassword
    )
}

suspend fun Application.configureDatabaseSchema() = suspendTransaction {
    exec("CREATE SCHEMA IF NOT EXISTS hdt AUTHORIZATION $dbUser;")
    exec("SET search_path TO hdt;")
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
