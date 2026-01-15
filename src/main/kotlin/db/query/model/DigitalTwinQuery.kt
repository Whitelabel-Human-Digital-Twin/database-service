package io.github.whdt.db.query.dsl

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("dtQuery")
data class DigitalTwinQuery(
    val select: SelectClause,
    val filters: List<Filter> = emptyList(),
    val aggregations: List<Aggregation> = emptyList(),
    val timeWindow: TimeWindow? = null,
    val groupBy: List<PropertyRef> = emptyList()
)