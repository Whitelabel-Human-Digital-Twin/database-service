package io.github.whdt.db.mappingEntities

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.datetime.*

object TimeTable : Table("time") {
    val id = integer("id").autoIncrement()
    val dateEnter = datetime("dateEnter")
    val dateStart = datetime("dateStart")
    val dateEnd = datetime("dateEnd")
    override val primaryKey = PrimaryKey(id)
}
