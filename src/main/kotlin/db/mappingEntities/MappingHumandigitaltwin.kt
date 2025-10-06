@file:Suppress("DEPRECATION")

package db.mappingEntities

import io.github.whdt.db.entities.HumanDigitalTwin
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.jdbc.transactions.experimental.newSuspendedTransaction

object HumanDigitalTwinTable : IntIdTable("humandigitaltwin") {
    val name = varchar("name", 50)
}

class HumanDigitalTwinDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<HumanDigitalTwinDAO>(HumanDigitalTwinTable)

    var name by HumanDigitalTwinTable.name
}

suspend fun <T> HumanDigitalTwinTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

fun daoToModel(dao: HumanDigitalTwinDAO) = HumanDigitalTwin(
    dao.name
)