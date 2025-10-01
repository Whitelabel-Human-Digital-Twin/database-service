package io.github.whdt.db.entità

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object PropertyTable : IntIdTable("property") {
    val nome = varchar("nome", 100)
    val descrizione = text("descrizione")
}
/*
class PropertyDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PropertyDAO>(PropertyTable)

    var nome by PropertyTable.nome
    var descrizione by PropertyTable.descrizione
}*/