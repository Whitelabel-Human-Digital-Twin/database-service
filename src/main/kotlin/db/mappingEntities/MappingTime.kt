package db.mappingEntities


import io.github.whdt.db.entities.Time
import org.jetbrains.exposed.v1.datetime.*
import org.jetbrains.exposed.v1.javatime.*
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass


object TimeTable : IntIdTable("time") {
    val dataInserimento = date("dataInserimento")
    val dataInizio = varchar("dataInizio", 100)
    val dataFine = varchar("dataFine", 100)
}

class TimeDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TimeDAO>(TimeTable)

    var dataInserimento by TimeTable.dataInserimento
    var dataInizio by TimeTable.dataInizio
    var dataFine by TimeTable.dataFine
}

fun daoToModel(dao: TimeDAO) = Time(
    dao.dataInserimento,
    dao.dataInizio,
    dao.dataFine
)