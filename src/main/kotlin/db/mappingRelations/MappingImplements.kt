package io.github.whdt.db.mappingRelations

import org.jetbrains.exposed.v1.core.Table

object ImplementsTable : Table("implements") {
    val property_id = integer( "property_id")
    val humandigitaltwin_id = integer( "humandigitaltwin_id")
}