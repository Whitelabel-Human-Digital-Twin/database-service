import io.github.whdt.PostgresHDTRepository
import io.github.whdt.db.configureDatabases
import io.github.whdt.db.entities.HumanDigitalTwin
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import io.ktor.server.testing.testApplication

class PostgresHDTRepositoryTest : FunSpec({

    lateinit var repository: PostgresHDTRepository

    beforeSpec {
        testApplication {
            application {
                configureDatabases()
            }
        }
        repository = PostgresHDTRepository()
    }

    test("prima proova data base ") {
        val newHDT = HumanDigitalTwin(name = "Test HDT")
        repository.addHDT(newHDT)

        val allHDTs = repository.allHDT()

        allHDTs.shouldNotBeEmpty()
        allHDTs.any { it.name == "Test HDT" } shouldBe true

        println("HDTs nel database: ${allHDTs.size}")
        allHDTs.forEach { println("- ${it.name}") }
    }
})