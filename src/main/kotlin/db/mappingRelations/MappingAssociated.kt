package io.github.whdt.db.mappingRelations

import org.jetbrains.exposed.v1.core.Table

object AssociatedTable : Table("associated") {
    val property_id = integer( "property_id")
    val interface_id = integer( "interface_id")
}