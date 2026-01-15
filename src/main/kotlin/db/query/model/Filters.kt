package io.github.whdt.db.query.dsl

import io.github.whdt.core.hdt.model.property.PropertyValue
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface Filter

@Serializable
@SerialName("compare")
data class ComparisonFilter(
    val property: PropertyRef,
    val op: ComparisonOp,
    val value: PropertyValue
) : Filter

@Serializable
@SerialName("in")
data class InFilter(
    val property: PropertyRef,
    val values: List<PropertyValue>
) : Filter

@Serializable
@SerialName("and")
data class AndFilter(
    val filters: List<Filter>
) : Filter

@Serializable
@SerialName("or")
data class OrFilter(
    val filters: List<Filter>
) : Filter
