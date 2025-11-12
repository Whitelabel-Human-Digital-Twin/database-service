package io.github.whdt.db.mappingRelations

import org.jetbrains.exposed.v1.core.Table

object SamplingTable : Table("associated") {
    val time_id = integer( "time_id")
    val value_id = integer( "interface_id")
}