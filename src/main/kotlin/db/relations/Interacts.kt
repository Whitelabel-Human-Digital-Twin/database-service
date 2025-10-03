package io.github.whdt.db.relations

import kotlinx.serialization.Serializable

@Serializable
data class Interacts(
    val humandigitaltwin_id : Int,
    val inteface_id : Int

)