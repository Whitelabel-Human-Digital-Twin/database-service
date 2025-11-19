import io.github.whdt.db.repository.PostgresHDTRepository
import io.github.whdt.db.configureDatabases
import io.github.whdt.db.entities.*
import io.github.whdt.db.relations.*
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import io.ktor.server.testing.testApplication
import kotlin.random.Random
import kotlinx.datetime.*

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

    test("Test Time dateEnter") {
        val testTime = LocalDateTime(
            Random.nextInt(2000, 2024),
            Random.nextInt(1, 13),
            Random.nextInt(1, 29),
            Random.nextInt(0, 24),
            Random.nextInt(0, 60),
            Random.nextInt(0, 60)
        )
        val newTime = Time( dateenter = testTime)
        repository.addTime(newTime)

        val allTime = repository.allTime()

        allTime.shouldNotBeEmpty()
        allTime.any { it.dateenter == testTime } shouldBe true
    }

    test("Test Time dateStart dateEnter") {
        val testTime1 = LocalDateTime(
            Random.nextInt(2000, 2100),
            Random.nextInt(1, 13),
            Random.nextInt(1, 29),
            Random.nextInt(0, 24),
            Random.nextInt(0, 60),
            Random.nextInt(0, 60)
        )
        val testTime2 = LocalDateTime(
            Random.nextInt(1900, 1999),
            Random.nextInt(1, 13),
            Random.nextInt(1, 29),
            Random.nextInt(0, 24),
            Random.nextInt(0, 60),
            Random.nextInt(0, 60)
        )
        val newTime = Time( datestart = testTime1, dateend = testTime2)
        repository.addTime(newTime)

        val allTime = repository.allTime()

        allTime.shouldNotBeEmpty()
        allTime.any { it.datestart == testTime1 } shouldBe true
        allTime.any { it.dateend == testTime2 } shouldBe true
    }

    test("Test Value ") {
        val testName = "Value_${Random.nextInt(100001)}"
        val newValue = Value(name = testName, value = "10", type = "Int" )
        repository.addValue(newValue)

        val allValue = repository.allValue()

        allValue.shouldNotBeEmpty()
        allValue.any { it.name == testName } shouldBe true
    }

    test("Test Associated ") {
        val testid1 = repository.allProperty().get(0).component1()
        val testid2 = repository.allInterface().get(0).component1()
        val newAssociated = Associated(property_id = testid1, interface_id = testid2)
        repository.addAssociated(newAssociated)

        val allAssociated = repository.allAssociated()

        allAssociated.shouldNotBeEmpty()
        allAssociated.any { it.property_id == testid1 } shouldBe true
        allAssociated.any { it.interface_id == testid2 } shouldBe true
    }

    test("Test Defines ") {
        val testid1 = repository.allProperty().get(0).component1()
        val testid2 = repository.allValue().get(0).component1()
        val newDefines = Defines(property_id = testid1, value_id = testid2)
        repository.addDefines(newDefines)

        val allDefines = repository.allDefines()

        allDefines.shouldNotBeEmpty()
        allDefines.any { it.property_id == testid1 } shouldBe true
        allDefines.any { it.value_id == testid2 } shouldBe true
    }

    test("Test Implements ") {
        val testid1 = repository.allProperty().get(0 ).component1()
        val testid2 = repository.allHDT().get(0).component1()
        val newImplements = Implements(property_id = testid1, humandigitaltwin_id = testid2)
        repository.addImplements(newImplements)

        val allImplements = repository.allImplements()

        allImplements.shouldNotBeEmpty()
        allImplements.any { it.property_id == testid1 } shouldBe true
        allImplements.any { it.humandigitaltwin_id == testid2 } shouldBe true
    }

    test("Test Interacts ") {
        val testid1 = repository.allHDT().get(0).component1()
        val testid2 = repository.allInterface().get(0).component1()
        val newInteracts = Interacts(humandigitaltwin_id = testid1, interface_id = testid2)
        repository.addInteracts(newInteracts)

        val allInteracts = repository.allInteracts()

        allInteracts.shouldNotBeEmpty()
        allInteracts.any { it.humandigitaltwin_id == testid1 } shouldBe true
        allInteracts.any { it.interface_id == testid2 } shouldBe true
    }

    test("Test Sampling ") {
        val testid1 = repository.allTime().get(0).component1()
        val testid2 = repository.allValue().get(0).component1()
        val newSampling = Sampling(time_id = testid1, value_id = testid2)
        repository.addSampling(newSampling)

        val allSampling = repository.allSampling()

        allSampling.shouldNotBeEmpty()
        allSampling.any { it.time_id == testid1 } shouldBe true
        allSampling.any { it.value_id == testid2 } shouldBe true
    }

})