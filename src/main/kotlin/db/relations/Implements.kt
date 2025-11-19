package io.github.whdt.db.relations

import kotlinx.serialization.Serializable

@Serializable
data class Implements(
    val id: Int = 0,
    val property_id : Int,
    val humandigitaltwin_id : Int

)