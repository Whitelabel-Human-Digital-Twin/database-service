package io.github.whdt.db.mappingRelations

import org.jetbrains.exposed.v1.core.Table

object DefinesTable : Table("defines") {
    val property_id = integer( "property_id")
    val value_id = integer( "value_id")
}