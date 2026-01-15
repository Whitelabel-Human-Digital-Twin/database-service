package io.github.whdt.db.query.dsl

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("aggregate")
data class Aggregation(
    val property: PropertyRef,
    val function: AggregationFunction,
    val alias: String? = null
)

@Serializable
enum class AggregationFunction {
    @SerialName("avg") AVG,
    @SerialName("min") MIN,
    @SerialName("max") MAX,
    @SerialName("sum") SUM,
    @SerialName("count") COUNT
}
