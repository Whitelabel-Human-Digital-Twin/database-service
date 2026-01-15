package io.github.whdt.db.query.dsl

import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@DtQueryDsl
@OptIn(ExperimentalTime::class)
class TimeWindowBuilder {
    private var from: Instant? = null
    private var to: Instant? = null

    /** Explicit bounds */
    fun between(start: Instant, end: Instant) {
        require(start < end) { "start must be before end" }
        from = start
        to = end
    }

    /** Relative window */
    fun last(duration: Duration) {
        require(!duration.isNegative() && duration.isFinite()) {
            "duration must be positive and finite"
        }

        to = Clock.System.now()
        from = to!! - duration
    }

    /** Convenience helpers */
    fun lastHours(hours: Int) = last(hours.hours)
    fun lastDays(days: Int) = last(days.days)

    fun build(): TimeWindow =
        TimeWindow(
            from = from ?: error("from is required"),
            to = to ?: error("to is required")
        )
}
