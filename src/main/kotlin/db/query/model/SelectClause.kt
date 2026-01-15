package io.github.whdt.db.query.dsl

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface SelectClause

@Serializable
@SerialName("selectDtIds")
object SelectDtIds : SelectClause

@Serializable
@SerialName("selectProperties")
data class SelectProperties(
    val properties: List<PropertyRef>
) : SelectClause
