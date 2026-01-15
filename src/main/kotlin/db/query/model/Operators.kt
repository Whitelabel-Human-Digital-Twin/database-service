package db.query.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ComparisonOp {
    @SerialName("eq") EQ,
    @SerialName("neq") NEQ,
    @SerialName("gt") GT,
    @SerialName("gte") GTE,
    @SerialName("lt") LT,
    @SerialName("lte") LTE
}
