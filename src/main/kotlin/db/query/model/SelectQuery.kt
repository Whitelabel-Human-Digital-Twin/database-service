package db.query.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("select")
data class SelectQuery(
    override val where: WhereClause? = null,
    override val timeWindow: TimeWindow? = null,
    val select: SelectClause
) : DtQuery()

@Serializable
data class SelectClause(
    val fields: List<SelectField>
)

@Serializable
sealed class SelectField {
    @Serializable @SerialName("id")
    object Id : SelectField()

    @Serializable @SerialName("property")
    data class Property(val ref: PropertyRef) : SelectField()
}
