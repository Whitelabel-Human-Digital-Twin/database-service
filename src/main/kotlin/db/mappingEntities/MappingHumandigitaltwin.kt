package io.github.whdt.db.mappingEntities

import org.jetbrains.exposed.v1.core.Table

object HumanDigitalTwinTable : Table("hdt.humandigitaltwin") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50)
    override val primaryKey = PrimaryKey(id)
}