package db.query.dsl

import db.query.model.Aggregation
import db.query.model.Avg
import db.query.model.Count
import db.query.model.Max
import db.query.model.Min

@DtQueryDsl
class AggregateBuilder {

    private val aggs = mutableListOf<Aggregation>()

    fun avg(field: String, block: AggregationAliasBuilder.() -> Unit = {}) {
        val builder = AggregationAliasBuilder(field)
        builder.block()
        aggs += Avg(field, builder.alias)
    }

    fun min(field: String, block: AggregationAliasBuilder.() -> Unit = {}) {
        val builder = AggregationAliasBuilder(field)
        builder.block()
        aggs += Min(field, builder.alias)
    }

    fun max(field: String, block: AggregationAliasBuilder.() -> Unit = {}) {
        val builder = AggregationAliasBuilder(field)
        builder.block()
        aggs += Max(field, builder.alias)
    }

    fun count(field: String = "*", block: AggregationAliasBuilder.() -> Unit = {}) {
        val builder = AggregationAliasBuilder(field)
        builder.block()
        aggs += Count(field, builder.alias)
    }

    fun build(): List<Aggregation> = aggs
}

@DtQueryDsl
class AggregationAliasBuilder(private val field: String) {
    var alias: String? = null

    /** Set the alias using a property-like syntax */
    fun alias(name: String) {
        alias = name
    }
}
