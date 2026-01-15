package io.github.whdt.db.query.dsl

@DslMarker
annotation class DtQueryDsl

fun dtQuery(block: DigitalTwinQueryBuilder.() -> Unit): DigitalTwinQuery =
    DigitalTwinQueryBuilder().apply(block).build()
