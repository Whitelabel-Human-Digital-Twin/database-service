package db.query.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("aggregate")
data class AggregateQuery(
    override val where: WhereClause? = null,
    override val timeWindow: TimeWindow? = null,
    val aggregations: List<Aggregation>
) : DtQuery()
