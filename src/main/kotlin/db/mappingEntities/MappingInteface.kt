package db.mappingEntities

import io.github.whdt.db.entities.Interface
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.jdbc.transactions.experimental.newSuspendedTransaction

object InterfaceTable : IntIdTable("interface") {
    val name = varchar("name", 100)
    val ipAddress = varchar("ipAddress", 15)
    val port = integer( "port")
    val clientId = varchar("clientId", 50)
    val type = varchar("type", 50)

}

class InterfaceDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<InterfaceDAO>(InterfaceTable)

    var name by InterfaceTable.name
    var ipAddress by InterfaceTable.ipAddress
    var port by InterfaceTable.port
    var clientId by InterfaceTable.clientId
    var type by InterfaceTable.type
}

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

fun daoToModel(dao: InterfaceDAO) = Interface(
    dao.name,
    dao.ipAddress,
    dao.port,
    dao.clientId,
    dao.type
    )