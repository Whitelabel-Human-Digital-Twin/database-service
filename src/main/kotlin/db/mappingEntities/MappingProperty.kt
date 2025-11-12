package io.github.whdt.db.mappingEntities

import org.jetbrains.exposed.v1.core.Table

object PropertyTable : Table("property") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val description = text("description")
    override val primaryKey = PrimaryKey(id)
}