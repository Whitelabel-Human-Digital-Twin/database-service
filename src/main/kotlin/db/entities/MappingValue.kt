package io.github.whdt.db.entità

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object ValueTable : IntIdTable("value") {
    val Nome = varchar("nome", 100)
    val Valore = varchar("valore", 255)
    val Tipo = varchar("tipo", 50)

}
/*
class ValueDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ValueDAO>(ValueTable)

    var nome by ValueTable.nome
    var valore by ValueTable.valore
    var tipo by ValueTable.tipo
}*/