package io.github.whdt.db.entities

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Time(
    val id: Int = 0,
    val dateenter: LocalDateTime ? = null,
    val datestart: LocalDateTime ? = null,
    val dateend: LocalDateTime ? = null
)
