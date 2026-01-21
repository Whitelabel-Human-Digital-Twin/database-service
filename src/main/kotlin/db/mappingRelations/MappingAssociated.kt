package io.github.whdt.db.mappingRelations

import org.jetbrains.exposed.v1.core.Table

object AssociatedTable : Table("hdt.associated") {
    val id = integer("id").autoIncrement()
    val property_id = integer( "property_id")
    val interface_id = integer( "interface_id")
    override val primaryKey = PrimaryKey(id)
}