package io.github.whdt.db.entities

import kotlinx.serialization.Serializable

@Serializable
data class Interface(
    val name: String,
    val ipAddress: String,
    val port: Int,
    val clientId: String,
    val type: String
)