package io.github.whdt.db.entities

import kotlinx.serialization.Serializable

@Serializable
data class Value(
    val name: String,
    val value: String,
    val type: String
)