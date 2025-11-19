package io.github.whdt.db.mappingRelations

import org.jetbrains.exposed.v1.core.Table

object SamplingTable : Table("sampling") {
    val id = integer("id").autoIncrement()
    val time_id = integer( "time_id")
    val value_id = integer( "value_id")
    override val primaryKey = PrimaryKey(id)
}