package db.query.model

import kotlinx.serialization.Serializable

@Serializable
sealed class DtQuery {
    abstract val where: WhereClause?
    abstract val timeWindow: TimeWindow?
}
