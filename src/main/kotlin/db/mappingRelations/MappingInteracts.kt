package io.github.whdt.db.mappingRelations

import org.jetbrains.exposed.v1.core.Table

object InteractsTable : Table("interacts") {
    val interface_id = integer( "interface_id")
    val humandigitaltwin_id = integer( "humandigitaltwin_id")
}