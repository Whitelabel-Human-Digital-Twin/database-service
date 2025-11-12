import io.github.whdt.db.repository.PostgresHDTRepository
import io.github.whdt.db.configureDatabases
import io.github.whdt.db.entities.*
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import io.ktor.server.testing.testApplication
import kotlin.random.Random

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

    test("Test HumanDigitalTwinTable ") {
        val testName = "HDT_${Random.nextInt(100001)}"
        val newHDT = HumanDigitalTwin(name = testName)
        repository.addHDT(newHDT)

        val allHDTs = repository.allHDT()

        allHDTs.shouldNotBeEmpty()
        allHDTs.any { it.name == testName } shouldBe true
    }

    test("Test InterfaceTable ") {
        val testName = "Interface_${Random.nextInt(100001)}"
        val newInterface = Interface(name = testName, ipaddress = "127.0.0.1", port = 5 , clientid = "prova", type = "HTTP" )
        repository.addInterface(newInterface)

        val allInterface = repository.allInterface()

        allInterface.shouldNotBeEmpty()
        allInterface.any { it.name == testName } shouldBe true
    }

    test("Test Property ") {
        val testName = "Property_${Random.nextInt(100001)}"
        val newProperty = Property(name = testName, description = "Test description")
        repository.addProperty(newProperty)

        val allProperty = repository.allProperty()

        allProperty.shouldNotBeEmpty()
        allProperty.any { it.name == testName } shouldBe true
    }
})