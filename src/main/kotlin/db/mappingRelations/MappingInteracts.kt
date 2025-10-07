package io.github.whdt.db.mappingRelations

import io.github.whdt.db.relations.Interacts
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction

object InteractsTable : IntIdTable("interacts") {
    val interface_id = integer( "interface_id")
    val humandigitaltwin_id = integer( "humandigitaltwin_id")
}

class InteractsDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<InteractsDAO>(InteractsTable)

    var humandigitaltwin_id by InteractsTable.humandigitaltwin_id
    val interface_id by InteractsTable.interface_id
}

suspend fun <T> InteractsTransaction(block: Transaction.() -> T): T =
    suspendTransaction(statement = block)

fun daoToModel(dao: InteractsDAO) = Interacts(
    dao.humandigitaltwin_id,
    dao.interface_id
)