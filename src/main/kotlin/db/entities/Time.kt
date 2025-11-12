package io.github.whdt.db.entities

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Time(
    val id: Int = 0,
    val dateEnter: LocalDateTime,
    val dateStart: LocalDateTime,
    val dateEnd: LocalDateTime
)
