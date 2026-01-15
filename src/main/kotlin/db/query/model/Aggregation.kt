package db.query.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Aggregation {
    abstract val field: String
    abstract val alias: String?
}

@Serializable @SerialName("avg")
data class Avg(
    override val field: String,
    override val alias: String? = null
) : Aggregation()

@Serializable @SerialName("min")
data class Min(
    override val field: String,
    override val alias: String? = null
) : Aggregation()

@Serializable @SerialName("max")
data class Max(
    override val field: String,
    override val alias: String? = null
) : Aggregation()

@Serializable @SerialName("count")
data class Count(
    override val field: String,
    override val alias: String? = null
) : Aggregation()

