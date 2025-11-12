package io.github.whdt.db.entities

import kotlinx.serialization.Serializable

@Serializable
data class HumanDigitalTwin(
    val id: Int = 0,
    val name: String,
)