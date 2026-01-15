package db.query.dsl

import db.query.model.DtQuery

@DslMarker
annotation class DtQueryDsl

fun dtQuery(block: DtQueryBuilder.() -> Unit): DtQuery =
    DtQueryBuilder().apply(block).build()

