package io.github.whdt.db.query.dsl.filter

import io.github.whdt.db.query.dsl.AndFilter
import io.github.whdt.db.query.dsl.DtQueryDsl
import io.github.whdt.db.query.dsl.Filter
import io.github.whdt.db.query.dsl.PropertyRef

@DtQueryDsl
class FilterBuilder {

    private val filters = mutableListOf<Filter>()

    fun property(name: String): PropertyPredicate =
        PropertyPredicate(PropertyRef(name)) { filters += it }

    fun build(): Filter =
        when (filters.size) {
            0 -> error("Empty where clause")
            1 -> filters.first()
            else -> AndFilter(filters)
        }
}
