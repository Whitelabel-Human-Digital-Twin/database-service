package io.github.whdt.db.mappingEntities

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.datetime.*

object TimeTable : Table("hdt.time") {
    val id = integer("id").autoIncrement()
    val dateenter = datetime("dateenter").nullable()
    val datestart = datetime("datestart").nullable()
    val dateend = datetime("dateend").nullable()
    override val primaryKey = PrimaryKey(id)
}
