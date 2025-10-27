package db.mappingEntities

import io.github.whdt.db.JdbcTransactionManager
import io.github.whdt.db.entities.Time
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.datetime.*
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

object TimeTable : IntIdTable("time") {
    val dateEnter = datetime("dateEnter")
    val dateStart = datetime("dateStart")
    val dateEnd = datetime("dateEnd")
}

class TimeDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TimeDAO>(TimeTable)

    var dateEnter by TimeTable.dateEnter
    var dateStart by TimeTable.dateStart
    var dateEnd by TimeTable.dateEnd
}

suspend fun <T> TimeTransaction(block: suspend Transaction.() -> T): T =
    JdbcTransactionManager.execute(block)

fun daoToModel(dao: TimeDAO) = Time(
    dao.dateEnter,
    dao.dateStart,
    dao.dateEnd
)