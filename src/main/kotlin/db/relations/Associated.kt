package io.github.whdt.db.relations

import kotlinx.serialization.Serializable

@Serializable
data class Associated(
    val id: Int = 0,
    val property_id : Int,
    val interface_id : Int
)