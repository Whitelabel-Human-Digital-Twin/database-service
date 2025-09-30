package io.github.whdt.db

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object InterfaceTable : IntIdTable("interface") {
    val nome = varchar("name", 50)
    val storage = varchar("storage", 50)
}
/*
class InterfaceDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<InterfaceDAO>(InterfaceTable)

    var name by InterfaceTable.name
    var storage by InterfaceTable.storage
}*/