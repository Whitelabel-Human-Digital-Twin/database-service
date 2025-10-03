package io.github.whdt.db.mappingRelations

import io.github.whdt.db.relations.Associated
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.jdbc.transactions.experimental.newSuspendedTransaction

object AssociatedTable : IntIdTable("associated") {
    val property_id = integer( "property_id")
    val interface_id = integer( "interface_id")
}

class AssociatedDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AssociatedDAO>(AssociatedTable)

    var property_id by AssociatedTable.property_id
    val interface_id by AssociatedTable.interface_id
}

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

fun daoToModel(dao: AssociatedDAO) = Associated(
    dao.property_id,
    dao.interface_id
)