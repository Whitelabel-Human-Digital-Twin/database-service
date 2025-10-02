package io.github.whdt.db.entities

import kotlinx.serialization.Serializable

@Serializable
data class Time(
    val dateEnter
    val dateStart
    val dateEnd
)
