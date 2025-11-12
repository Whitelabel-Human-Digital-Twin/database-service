package io.github.whdt.db.entities

import kotlinx.serialization.Serializable

@Serializable
data class Value(
    val id: Int = 0,
    val name: String,
    val value: String,
    val type: String
)