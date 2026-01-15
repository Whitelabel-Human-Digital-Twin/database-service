package db.query.dsl.filter

import db.query.model.AndFilter
import db.query.dsl.DtQueryDsl
import db.query.model.Filter
import db.query.model.PropertyRef

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
