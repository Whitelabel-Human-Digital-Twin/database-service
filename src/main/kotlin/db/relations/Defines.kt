package io.github.whdt.db.relations

import kotlinx.serialization.Serializable

@Serializable
data class Defines(
    val id: Int = 0,
    val property_id : Int,
    val value_id : Int

)