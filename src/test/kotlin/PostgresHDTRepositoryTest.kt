import io.github.whdt.db.repository.PostgresHDTRepository
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

    test("prima prova data base ") {
        val newHDT = HumanDigitalTwin(name = "Test HDT2")
        repository.addHDT(newHDT)

        val allHDTs = repository.allHDT()

        allHDTs.shouldNotBeEmpty()
        allHDTs.any { it.name == "Test HDT2" } shouldBe true

        println("HDTs nel database: ${allHDTs.size}")
        allHDTs.forEach { println("- ${it.name}") }
    }
})