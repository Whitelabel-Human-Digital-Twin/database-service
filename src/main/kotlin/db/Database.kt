package io.github.whdt.db

import io.github.whdt.db.mappingEntities.HumanDigitalTwinTable
import io.github.whdt.db.mappingEntities.InterfaceTable
import io.github.whdt.db.mappingEntities.PropertyTable
import io.github.whdt.db.mappingEntities.TimeTable
import io.github.whdt.db.mappingEntities.ValueTable
import io.github.whdt.db.mappingRelations.AssociatedTable
import io.github.whdt.db.mappingRelations.DefinesTable
import io.github.whdt.db.mappingRelations.ImplementsTable
import io.github.whdt.db.mappingRelations.InteractsTable
import io.github.whdt.db.mappingRelations.SamplingTable
import io.ktor.server.application.Application
import kotlinx.coroutines.launch
import org.jetbrains.exposed.v1.core.transactions.transactionScope
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.SchemaUtils
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction

fun Application.configureDatabases() {
    R2dbcDatabase.connect(
        url = "${System.getenv("DATABASE_URL_BASE")}/${System.getenv("DATABASE_NAME")}",
        driver = "postgresql",
        user = System.getenv("DATABASE_USERNAME"),
        password = System.getenv("DATABASE_PASSWORD")
    )
}

fun Application.configureDatabaseSchema() {
    launch {
        suspendTransaction {
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
    }
}