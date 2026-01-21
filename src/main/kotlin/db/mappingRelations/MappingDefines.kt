package io.github.whdt.db.mappingRelations

import org.jetbrains.exposed.v1.core.Table

object DefinesTable : Table("hdt.defines") {
    val id = integer("id").autoIncrement()
    val property_id = integer( "property_id")
    val value_id = integer( "value_id")
    override val primaryKey = PrimaryKey(id)
}