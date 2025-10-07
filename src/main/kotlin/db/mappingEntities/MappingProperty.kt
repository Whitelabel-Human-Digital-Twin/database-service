package db.mappingEntities

import io.github.whdt.db.entities.Property
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction

object PropertyTable : IntIdTable("property") {
    val name = varchar("name", 100)
    val description = text("description")
}

class PropertyDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PropertyDAO>(PropertyTable)

    var name by PropertyTable.name
    var description by PropertyTable.description
}

suspend fun <T> PropertyTransaction(block: suspend Transaction.() -> T): T =
    suspendTransaction(statement = block)

fun daoToModel(dao: PropertyDAO) = Property(
    dao.name,
    dao.description
)