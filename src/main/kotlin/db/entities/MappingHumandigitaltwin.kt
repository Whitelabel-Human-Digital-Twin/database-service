package io.github.whdt.db.entities


import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

object HumanDigitalTwinTable : IntIdTable("humandigitaltwin") {
    val nome = varchar("nome", 50)
    val storage = varchar("storage", 50)
}

class HumanDigitalTwinDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<HumanDigitalTwinDAO>(HumanDigitalTwinTable)

    var nome by HumanDigitalTwinTable.nome
    var storage by HumanDigitalTwinTable.storage
}