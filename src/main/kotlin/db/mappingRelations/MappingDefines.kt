package io.github.whdt.db.mappingRelations

import io.github.whdt.db.relations.Defines
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction

object DefinesTable : IntIdTable("defines") {
    val property_id = integer( "property_id")
    val value_id = integer( "value_id")
}

class DefinesDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<DefinesDAO>(DefinesTable)

    var property_id by DefinesTable.property_id
    val value_id by DefinesTable.value_id
}

suspend fun <T> DefinesTransaction(block: Transaction.() -> T): T =
    suspendTransaction(statement = block)

fun daoToModel(dao: DefinesDAO) = Defines(
    dao.property_id,
    dao.value_id
)