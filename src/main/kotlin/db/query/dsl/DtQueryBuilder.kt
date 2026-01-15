package db.query.dsl

import db.query.dsl.filter.WhereBuilder
import db.query.model.AggregateQuery
import db.query.model.Aggregation
import db.query.model.DtQuery
import db.query.model.SelectClause
import db.query.model.SelectQuery
import db.query.model.TimeWindow
import db.query.model.WhereClause

@DtQueryDsl
class DtQueryBuilder {
    private var where: WhereClause? = null
    private var timeWindow: TimeWindow? = null
    private var select: SelectClause? = null
    private var aggregations: List<Aggregation>? = null

    fun where(block: WhereBuilder.() -> Unit) {
        where = WhereBuilder().apply(block).build()
    }

    fun timeWindow(block: TimeWindowBuilder.() -> Unit) {
        timeWindow = TimeWindowBuilder().apply(block).build()
    }

    fun select(block: SelectBuilder.() -> Unit) {
        check(aggregations == null) { "Cannot use select with aggregate" }
        select = SelectBuilder().apply(block).build()
    }

    fun aggregate(block: AggregateBuilder.() -> Unit) {
        check(select == null) { "Cannot use aggregate with select" }
        aggregations = AggregateBuilder().apply(block).build()
    }

    fun build(): DtQuery =
        when {
            select != null ->
                SelectQuery(where, timeWindow, select!!)
            aggregations != null ->
                AggregateQuery(where, timeWindow, aggregations!!)
            else ->
                error("Query must define either select or aggregate")
        }
}
