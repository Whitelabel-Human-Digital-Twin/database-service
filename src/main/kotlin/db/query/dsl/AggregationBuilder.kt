package io.github.whdt.db.query.dsl

class AggregationAliasBuilder(
    private val emit: (String?) -> Unit
) {
    infix fun alias(name: String) {
        emit(name)
    }
}

@DtQueryDsl
class AggregationBuilder {

    private var aggregation: Aggregation? = null

    fun avg(property: String): AggregationAliasBuilder =
        buildAgg(property, AggregationFunction.AVG)

    fun min(property: String): AggregationAliasBuilder =
        buildAgg(property, AggregationFunction.MIN)

    fun max(property: String): AggregationAliasBuilder =
        buildAgg(property, AggregationFunction.MAX)

    fun count(property: String = "*"): AggregationAliasBuilder =
        buildAgg(property, AggregationFunction.COUNT)

    private fun buildAgg(
        property: String,
        fn: AggregationFunction
    ): AggregationAliasBuilder =
        AggregationAliasBuilder { alias ->
            aggregation = Aggregation(PropertyRef(property), fn, alias)
        }

    fun build(): Aggregation =
        aggregation ?: error("No aggregation defined")
}
