package db.query.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("propertyRef")
data class PropertyRef(val property: String)
