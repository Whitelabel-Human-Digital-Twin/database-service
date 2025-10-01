package io.github.whdt.db.entità


import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object TimeTable : IntIdTable("time") {
    // da vedere se esiste un tipo per le date
    val DataInserimento = varchar("dataInserimento", 100)
    val DataInizio = varchar("dataInizio", 100)
    val DataFine = varchar("dataFine", 100)

}
/*
class TimeDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TimeDAO>(TimeTable)

    var name by TimeTable.nome
    var storage by TimeTable.indirizzoIP
    var porta by TimeTable.porta
    var clientId by InterfaceTable.clientId
    var tipo by InterfaceTable.tipo
}*/