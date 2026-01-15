package db.query.dsl.filter

import io.github.whdt.core.hdt.model.property.PropertyValue
import io.github.whdt.core.hdt.model.property.PropertyValue.*
import db.query.model.ComparisonFilter
import db.query.model.ComparisonOp
import db.query.model.Filter
import db.query.model.InFilter
import db.query.model.PropertyRef

class PropertyPredicate(
    private val property: PropertyRef,
    private val emit: (Filter) -> Unit
) {

    infix fun eq(value: String) =
        emit(compare(ComparisonOp.EQ, StringPropertyValue(value)))

    infix fun eq(value: Number) =
        emit(compare(ComparisonOp.EQ, DoublePropertyValue(value.toDouble())))

    infix fun gt(value: Number) =
        emit(compare(ComparisonOp.GT, DoublePropertyValue(value.toDouble())))

    infix fun lt(value: Number) =
        emit(compare(ComparisonOp.LT, DoublePropertyValue(value.toDouble())))

    fun inside(vararg values: String) =
        emit(InFilter(property, values.map(::StringPropertyValue)))

    private fun compare(op: ComparisonOp, value: PropertyValue): Filter =
        ComparisonFilter(property, op, value)
}
