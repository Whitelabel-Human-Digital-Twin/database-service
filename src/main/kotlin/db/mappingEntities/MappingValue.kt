package io.github.whdt.db.mappingEntities

import org.jetbrains.exposed.v1.core.Table

object ValueTable : Table("value") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val value = varchar("value", 255)
    val type = varchar("type", 50)
    override val primaryKey = PrimaryKey(id)
}