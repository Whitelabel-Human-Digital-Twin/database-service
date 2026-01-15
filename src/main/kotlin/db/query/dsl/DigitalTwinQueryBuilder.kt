package io.github.whdt.db.query.dsl

import io.github.whdt.db.query.dsl.filter.FilterBuilder

@DtQueryDsl
class DigitalTwinQueryBuilder {

    private var selectClause: SelectClause? = null
    private val filters = mutableListOf<Filter>()
    private val aggregations = mutableListOf<Aggregation>()
    private var timeWindow: TimeWindow? = null
    private val groupBy = mutableListOf<PropertyRef>()

    fun selectDtIds() {
        selectClause = SelectDtIds
    }

    fun selectProperties(vararg names: String) {
        selectClause = SelectProperties(names.map(::PropertyRef))
    }

    fun where(block: FilterBuilder.() -> Unit) {
        filters += FilterBuilder().apply(block).build()
    }

    fun aggregate(block: AggregationBuilder.() -> Unit) {
        aggregations += AggregationBuilder().apply(block).build()
    }

    fun timeWindow(block: TimeWindowBuilder.() -> Unit) {
        timeWindow = TimeWindowBuilder().apply(block).build()
    }

    fun groupBy(vararg names: String) {
        groupBy += names.map(::PropertyRef)
    }

    fun build(): DigitalTwinQuery =
        DigitalTwinQuery(
            select = selectClause
                ?: error("select clause is mandatory"),
            filters = filters,
            aggregations = aggregations,
            timeWindow = timeWindow,
            groupBy = groupBy
        )
}
