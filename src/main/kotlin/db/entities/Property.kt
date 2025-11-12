package io.github.whdt.db.entities

import kotlinx.serialization.Serializable

@Serializable
data class Property(
    val id: Int = 0,
    val name: String,
    val description: String
)