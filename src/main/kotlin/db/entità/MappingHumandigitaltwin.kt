package io.github.whdt.db.entità


import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object HumanDigitalTwinTable : IntIdTable("humandigitaltwin") {
    val nome = varchar("nome", 50)
    val storage = varchar("storage", 50)
}

class HumanDigitalTwinDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<HumanDigitalTwinDAO>(HumanDigitalTwinTable)

    var nome by HumanDigitalTwinTable.nome
    var storage by HumanDigitalTwinTable.storage
}