package db.mappingEntities

import io.github.whdt.db.JdbcTransactionManager
import io.github.whdt.db.entities.HumanDigitalTwin
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

object HumanDigitalTwinTable : IntIdTable("humandigitaltwin") {
    val name = varchar("name", 50)
}

class HumanDigitalTwinDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<HumanDigitalTwinDAO>(HumanDigitalTwinTable)

    var name by HumanDigitalTwinTable.name
}

suspend fun <T> HumanDigitalTwinTransaction(block: Transaction.() -> T): T =
    JdbcTransactionManager.execute(block)

fun daoToModel(dao: HumanDigitalTwinDAO) = HumanDigitalTwin(
    dao.name
)