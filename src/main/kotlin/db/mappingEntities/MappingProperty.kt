package db.mappingEntities

import io.github.whdt.db.entities.Property
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.jdbc.transactions.experimental.newSuspendedTransaction

object PropertyTable : IntIdTable("property") {
    val name = varchar("name", 100)
    val description = text("description")
}

class PropertyDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PropertyDAO>(PropertyTable)

    var name by PropertyTable.name
    var description by PropertyTable.description
}

suspend fun <T> suspendTransaction(block: suspend Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

fun daoToModel(dao: PropertyDAO) = Property(
    dao.name,
    dao.description
)