package io.github.whdt.db.entità

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object InterfaceTable : IntIdTable("interface") {
    val nome = varchar("nome", 100)
    val indirizzoIP = varchar("indirizzoIP", 15)
    val porta = integer(name = "porta")
    val clientId = varchar("clientId", 50)
    val tipo = varchar("tipo", 50)

}
/*
class InterfaceDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<InterfaceDAO>(InterfaceTable)

    var name by InterfaceTable.nome
    var indirizzoIP by InterfaceTable.indirizzoIP
    var porta by InterfaceTable.porta
    var clientId by InterfaceTable.clientId
    var tipo by InterfaceTable.tipo
}*/