package io.github.whdt.db.mappingRelations

import io.github.whdt.db.JdbcTransactionManager
import io.github.whdt.db.relations.Associated
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

object AssociatedTable : IntIdTable("associated") {
    val property_id = integer( "property_id")
    val interface_id = integer( "interface_id")
}

class AssociatedDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AssociatedDAO>(AssociatedTable)

    var property_id by AssociatedTable.property_id
    var interface_id by AssociatedTable.interface_id
}

suspend fun <T> AssociatedTransaction(block: Transaction.() -> T): T =
    JdbcTransactionManager.execute(block)

fun daoToModel(dao: AssociatedDAO) = Associated(
    dao.property_id,
    dao.interface_id
)