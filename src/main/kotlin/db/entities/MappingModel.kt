package io.github.whdt.db.entità

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object ModelTable : IntIdTable("model") {
    val nome = varchar("nome", 100)

}
/*
class ModelDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ModelDAO>(ModelTable)

    var nome by ModelTable.nome
}*/