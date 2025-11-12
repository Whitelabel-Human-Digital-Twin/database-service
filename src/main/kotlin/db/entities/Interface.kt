package io.github.whdt.db.entities

import kotlinx.serialization.Serializable

@Serializable
data class Interface(
    val id: Int = 0,
    val name: String,
    val ipaddress: String,
    val port: Int,
    val clientid: String,
    val type: String
)