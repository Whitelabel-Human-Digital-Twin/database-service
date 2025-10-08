package io.github.whdt.db.relations

import kotlinx.serialization.Serializable

@Serializable
data class Associated(
    val property_id : Int,
    val inteface_id : Int
)