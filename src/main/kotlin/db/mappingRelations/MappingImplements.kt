package io.github.whdt.db.mappingRelations

import io.github.whdt.db.relations.Implements
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction

object ImplementsTable : IntIdTable("implements") {
    val property_id = integer( "property_id")
    val humandigitaltwin_id = integer( "humandigitaltwin_id")
}

class ImplementsDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AssociatedDAO>(ImplementsTable)

    var property_id by ImplementsTable.property_id
    val humandigitaltwin_id by ImplementsTable.humandigitaltwin_id
}

suspend fun <T> ImplementsTransaction(block: Transaction.() -> T): T =
    suspendTransaction(statement = block)

fun daoToModel(dao: ImplementsDAO) = Implements(
    dao.property_id,
    dao.humandigitaltwin_id
)