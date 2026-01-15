package query

import db.query.dsl.dtQuery
import io.kotest.core.spec.style.FunSpec
import kotlinx.serialization.json.Json

class QueryDslTest: FunSpec({

    test("select query") {
        val query = dtQuery {
            select {
                id()
                property("temperature")
            }

            where {
                property("status") eq "active"
                property("batteryLevel") lt 20
                property("location").inside("NY", "SF")
            }

            timeWindow {
                lastDays(1)
            }
        }

        println(query)

        val json = Json { prettyPrint = true }
        println(json.encodeToString(query))
    }

    test("aggregate query") {
        val query = dtQuery {
            aggregate {
                avg("temperature"){ alias("avg_temperature") }
            }
            where {
                property("status") eq "active"
                property("batteryLevel") lt 20
                property("location").inside("NY", "SF")
            }
        }
    }
})