package io.github.whdt.db.query.dsl

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("propertyRef")
data class PropertyRef(val property: String)
