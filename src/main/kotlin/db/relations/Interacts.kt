package io.github.whdt.db.relations

import kotlinx.serialization.Serializable

@Serializable
data class Interacts(
    val id: Int = 0,
    val humandigitaltwin_id : Int,
    val interface_id : Int

)