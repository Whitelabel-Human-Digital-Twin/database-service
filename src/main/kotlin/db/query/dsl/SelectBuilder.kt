package db.query.dsl

import db.query.model.PropertyRef
import db.query.model.SelectClause
import db.query.model.SelectField

@DtQueryDsl
class SelectBuilder {

    private val fields = mutableListOf<SelectField>()

    /** Select the DT id */
    fun id() {
        fields += SelectField.Id
    }

    /** Select a property by name */
    fun property(name: String) {
        fields += SelectField.Property(PropertyRef(name))
    }

    /** Select multiple properties at once */
    fun properties(vararg names: String) {
        names.forEach { property(it) }
    }

    /** Build the final immutable SelectClause */
    fun build(): SelectClause = SelectClause(fields.toList())
}
