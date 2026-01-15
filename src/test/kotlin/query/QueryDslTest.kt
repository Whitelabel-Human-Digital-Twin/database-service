package query

import io.github.whdt.db.query.dsl.dtQuery
import io.kotest.core.spec.style.FunSpec
import kotlinx.serialization.json.Json

class QueryDslTest: FunSpec({

    test("Dsl Builder") {
        val query = dtQuery {
            selectDtIds()

            where {
                property("status") eq "active"
                property("batteryLevel") lt 20
            }

            aggregate {
                avg("temperature") alias "avg_temp"
            }

            timeWindow {
                lastDays(1)
            }
        }
        println(query)

        val json = Json { prettyPrint = true }
        println(json.encodeToString(query))
    }
})