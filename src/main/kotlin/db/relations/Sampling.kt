package io.github.whdt.db.relations

import kotlinx.serialization.Serializable

@Serializable
data class Sampling(
    val id: Int = 0,
    val time_id : Int,
    val value_id : Int

)