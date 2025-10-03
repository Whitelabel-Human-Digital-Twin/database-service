package db.mappingEntities

import io.github.whdt.db.entities.Value
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.jdbc.transactions.experimental.newSuspendedTransaction

object ValueTable : IntIdTable("value") {
    val name = varchar("name", 100)
    val value = varchar("value", 255)
    val type = varchar("type", 50)

}

class ValueDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ValueDAO>(ValueTable)

    var name by ValueTable.name
    var value by ValueTable.value
    var type by ValueTable.type
}

suspend fun <T> suspendTransaction(block: suspend Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

fun daoToModel(dao: ValueDAO) = Value(
    dao.name,
    dao.value,
    dao.type
)