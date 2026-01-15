package db.query.dsl.filter

import db.query.dsl.DtQueryDsl
import db.query.model.AndFilter
import db.query.model.Filter
import db.query.model.PropertyRef
import db.query.model.WhereClause

@DtQueryDsl
class WhereBuilder {

    private val filters = mutableListOf<Filter>()

    /** Start building a filter for a property */
    fun property(name: String): PropertyPredicate =
        PropertyPredicate(PropertyRef(name)) { filters += it }

    /** Combine all filters into a single WhereClause */
    fun build(): WhereClause =
        when (filters.size) {
            0 -> error("Where clause cannot be empty")
            1 -> WhereClause(listOf(filters.first()))
            else -> WhereClause(listOf(AndFilter(filters)))
        }
}
