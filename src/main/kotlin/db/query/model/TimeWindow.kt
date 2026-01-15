package db.query.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Serializable
@SerialName("timeWindow")
@OptIn(ExperimentalTime::class)
data class TimeWindow(
    val from: Instant,
    val to: Instant
)
