import io.github.whdt.db.configureDatabaseSchema
import io.github.whdt.db.repository.PostgresHDTRepository
import io.github.whdt.db.configureDatabases
import io.github.whdt.db.entities.*
import io.github.whdt.db.relations.*
import io.kotest.common.runBlocking
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import io.ktor.server.testing.testApplication
import kotlin.random.Random
import kotlinx.datetime.*
import org.junit.jupiter.api.fail

class PostgresHDTRepositoryTest : FunSpec({

    lateinit var repository: PostgresHDTRepository

    beforeSpec {
        testApplication {
            application {
                configureDatabases()
                runBlocking { configureDatabaseSchema() }
            }
        }
        repository = PostgresHDTRepository()
    }

    test("Test HumanDigitalTwinTable ") {
        val testName = "HDT_${Random.nextInt(100001)}"
        val hdt = repository.allHDT().lastOrNull()
        var id = 0
        if (hdt != null) {
            id = hdt.id + 1
        }
        val newHDT = HumanDigitalTwin(id = id ,name = testName)
        repository.addHDT(newHDT)

        val allHDTs = repository.allHDT()

        allHDTs.shouldNotBeEmpty()
        allHDTs.any { it.name == testName } shouldBe true
    }

    test("Test InterfaceTable ") {
        val testName = "Interface_${Random.nextInt(100001)}"
        val randIp = "${Random.nextInt(256)}.${Random.nextInt(256)}.${Random.nextInt(256)}.${Random.nextInt(256)}"
        val int = repository.allInterface().lastOrNull()
        var id = 0
        if (int != null) {
            id = int.id + 1
        }
        val newInterface = Interface(id = id,name = testName, ipaddress = randIp , port = 5 , clientid = "prova", type = "HTTP" )
        repository.addInterface(newInterface)

        val allInterface = repository.allInterface()

        allInterface.shouldNotBeEmpty()
        allInterface.any { it.name == testName } shouldBe true
    }

    test("Test Property ") {
        val testName = "Property_${Random.nextInt(100001)}"
        val prop = repository.allProperty().lastOrNull()
        var id = 0
        if (prop != null) {
            id = prop.id + 1
        }
        val newProperty = Property(id = id,name = testName, description = "Test description")
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
        val time = repository.allTime().lastOrNull()
        var id = 0
        if (time != null) {
            id = time.id + 1
        }
        val newTime = Time(id = id, dateenter = testTime)
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
        val time = repository.allTime().lastOrNull()
        var id = 0
        if (time != null) {
            id = time.id + 1
        }
        val newTime = Time(id = id, datestart = testTime1, dateend = testTime2)
        repository.addTime(newTime)

        val allTime = repository.allTime()

        allTime.shouldNotBeEmpty()
        allTime.any { it.datestart == testTime1 } shouldBe true
        allTime.any { it.dateend == testTime2 } shouldBe true
    }

    test("Test Value ") {
        val testName = "Value_${Random.nextInt(100001)}"
        val valu = repository.allValue().lastOrNull()
        var id = 0
        if (valu != null) {
            id = valu.id + 1
        }
        val newValue = Value(id = id,name = testName, value = "${Random.nextInt(100001)}", type = "Int" )
        repository.addValue(newValue)

        val allValue = repository.allValue()

        allValue.shouldNotBeEmpty()
        allValue.any { it.name == testName } shouldBe true
    }

    test("Test Associated ") {
        val testName1 = "Property_${Random.nextInt(100001)}"
        val prop = repository.allProperty().lastOrNull()
        var id = 0
        if (prop != null) {
            id = prop.id + 1
        }
        val newProperty = Property(id = id,name = testName1, description = "Test description")
        repository.addProperty(newProperty)
        val testid1 = repository.allProperty().last().component1()

        val testName2 = "Interface_${Random.nextInt(100001)}"
        val randIp = "${Random.nextInt(256)}.${Random.nextInt(256)}.${Random.nextInt(256)}.${Random.nextInt(256)}"
        val int = repository.allInterface().lastOrNull()
        var id2 = 0
        if (int != null) {
            id2 = int.id + 1
        }
        val newInterface = Interface(id =id2,name = testName2, ipaddress = randIp, port = 5 , clientid = "prova", type = "HTTP" )
        repository.addInterface(newInterface)
        val testid2 = repository.allInterface().last().component1()

        val newAssociated = Associated(id = Random.nextInt(100001),property_id = testid1, interface_id = testid2)
        repository.addAssociated(newAssociated)

        val allAssociated = repository.allAssociated()

        allAssociated.shouldNotBeEmpty()
        allAssociated.any { (it.property_id == testid1) and  (it.interface_id == testid2) } shouldBe true
    }

    test("Test Defines ") {
        val testName1 = "Property_${Random.nextInt(100001)}"
        val prop = repository.allProperty().lastOrNull()
        var id = 0
        if (prop != null) {
            id = prop.id + 1
        }
        val newProperty = Property(id = id,name = testName1, description = "Test description")
        repository.addProperty(newProperty)
        val testid1 = repository.allProperty().last().component1()

        val testName2 = "Value_${Random.nextInt(100001)}"
        val valu = repository.allValue().lastOrNull()
        var id2 = 0
        if (valu != null) {
            id2 = valu.id + 1
        }
        val newValue = Value(id = id2,name = testName2, value = "10", type = "Int" )
        repository.addValue(newValue)
        val testid2 = repository.allValue().last().component1()
        val newDefines = Defines(id = Random.nextInt(100001), property_id = testid1, value_id = testid2)
        repository.addDefines(newDefines)

        val allDefines = repository.allDefines()

        allDefines.shouldNotBeEmpty()
        allDefines.any { (it.property_id == testid1) and (it.value_id == testid2) } shouldBe true
    }

    test("Test Implements ") {
        val testName1 = "Property_${Random.nextInt(100001)}"
        val prop = repository.allProperty().lastOrNull()
        var id = 0
        if (prop != null) {
            id = prop.id + 1
        }
        val newProperty = Property(id = id,name = testName1, description = "Test description")
        repository.addProperty(newProperty)
        val testid1 = repository.allProperty().last().component1()

        val testName2 = "HDT_${Random.nextInt(100001)}"
        val hdt = repository.allHDT().lastOrNull()
        var id2 = 0
        if (hdt != null) {
            id2 = hdt.id + 1
        }
        val newHDT = HumanDigitalTwin(id=id2,name = testName2)
        repository.addHDT(newHDT)
        val testid2 = repository.allHDT().last().component1()

        val newImplements = Implements(id = Random.nextInt(100001),property_id = testid1, humandigitaltwin_id = testid2)
        repository.addImplements(newImplements)

        val allImplements = repository.allImplements()

        allImplements.shouldNotBeEmpty()
        allImplements.any { (it.property_id == testid1) and (it.humandigitaltwin_id == testid2) } shouldBe true
    }

    test("Test Interacts ") {
        val testName1 = "HDT_${Random.nextInt(100001)}"
        val hdt = repository.allHDT().lastOrNull()
        var id = 0
        if (hdt != null) {
            id = hdt.id + 1
        }
        val newHDT = HumanDigitalTwin(id=id,name = testName1)
        repository.addHDT(newHDT)
        val testid1 = repository.allHDT().last().component1()

        val testName2 = "Interface_${Random.nextInt(100001)}"
        val randIp = "${Random.nextInt(256)}.${Random.nextInt(256)}.${Random.nextInt(256)}.${Random.nextInt(256)}"
        val int = repository.allInterface().lastOrNull()
        var id2 = 0
        if (int != null) {
            id2 = int.id + 1
        }
        val newInterface = Interface(id= id2,name = testName2, ipaddress = randIp, port = 5 , clientid = "prova", type = "HTTP" )
        repository.addInterface(newInterface)
        val testid2 = repository.allInterface().last().component1()

        val newInteracts = Interacts(id = Random.nextInt(100001),humandigitaltwin_id = testid1, interface_id = testid2)
        repository.addInteracts(newInteracts)

        val allInteracts = repository.allInteracts()

        allInteracts.shouldNotBeEmpty()
        allInteracts.any { (it.humandigitaltwin_id == testid1) and (it.interface_id == testid2) } shouldBe true
    }

    test("Test Sampling ") {
        val testTime = LocalDateTime(
            Random.nextInt(2000, 2024),
            Random.nextInt(1, 13),
            Random.nextInt(1, 29),
            Random.nextInt(0, 24),
            Random.nextInt(0, 60),
            Random.nextInt(0, 60)
        )
        val time = repository.allTime().lastOrNull()
        var id = 0
        if (time != null) {
            id = time.id + 1
        }
        val newTime = Time(id = id,dateenter = testTime)
        repository.addTime(newTime)
        val testid1 = repository.allTime().last().component1()

        val testName = "Value_${Random.nextInt(100001)}"
        val valu = repository.allValue().lastOrNull()
        var id2 = 0
        if (valu != null) {
            id2 = valu.id + 1
        }
        val newValue = Value(id = id2,name = testName, value = "${Random.nextInt(100001)}", type = "Int")
        repository.addValue(newValue)
        val testid2 = repository.allValue().last().component1()

        val newSampling = Sampling(id = Random.nextInt(100001), time_id = testid1, value_id = testid2)
        repository.addSampling(newSampling)
        val allSampling = repository.allSampling()

        allSampling.shouldNotBeEmpty()
        allSampling.any { (it.time_id == testid1) and (it.value_id == testid2) } shouldBe true
    }

    test("Test search value by time ") {
        val testTimeLess = LocalDateTime(2000,10,15,0,0,0)
        val testTimeGreater = LocalDateTime(2030,10,15,0,0,0)
        val id_detTime = repository.detTime(testTimeLess, testTimeGreater)
        id_detTime.shouldNotBeEmpty()
        id_detTime.forEach { println("$it" ) }
        val valu = repository.valueOfTime(testTimeLess, testTimeGreater)
        if(valu != null) {
            valu.shouldNotBeEmpty()
            valu.forEach { value -> println("valore: ${value.component1()}, ${value.component2()}, ${value.component3()}, ${value.component4()}") }
        }else fail("errore")
    }

    test("Test search min value of property by time ") {
        val testTimeLess = LocalDateTime(2000,10,15,0,0,0)
        val testTimeGreater = LocalDateTime(2029,10,15,0,0,0)
        val nameProp = "Battito_Cardiaco"
        val valu = repository.minPropertyOfTimeHdt("Mario_Rossi",nameProp,testTimeLess,testTimeGreater)
        if(valu != null) {
            println("il valore più basso è $valu")
        }else fail("errore")

    }

    test("Test search max value of property by time ") {
        val testTimeLess = LocalDateTime(2000,10,15,0,0,0)
        val testTimeGreater = LocalDateTime(2029,10,15,0,0,0)
        val nameProp = "Battito_Cardiaco"
        val valu = repository.maxPropertyOfTimeHdt("Mario_Rossi",nameProp,testTimeLess,testTimeGreater)
        if(valu != null) {
            println("il valore più alto è $valu")
        }else fail("errore")

    }

    test("Test search avg value of property by time ") {
        val testTimeLess = LocalDateTime(2000,10,15,0,0,0)
        val testTimeGreater = LocalDateTime(2029,10,15,0,0,0)
        val nameProp = "Battito_Cardiaco"
        val valu = repository.avgPropertyOfTime("Mario_Rossi",nameProp,testTimeLess,testTimeGreater)
        if(valu != null) {
            println("il valore più alto è $valu")
        }else fail("errore")

    }

    test("Test search property in  time ") {
        val testTimeLess = LocalDateTime(2000,10,15,0,0,0)
        val testTimeGreater = LocalDateTime(2025,12,27,15,40,0)
        print("va?22?")
        val valu = repository.valueOfTime(testTimeLess,testTimeGreater)
        print("va??")
        valu?.forEach { println("il valore : $it")  }
        if(valu != null) {
            valu.forEach { println("il valore : $it")  }
        }else fail("errore")

    }

    test("Test search dt "){
        val testName1 = "Property_${Random.nextInt(100001)}"
        val prop1 = repository.allProperty().lastOrNull()
        var id = 0
        if (prop1 != null) {
            id = prop1.id + 1
        }
        val newProperty1 = Property(id= id,name = testName1, description = "Test description")
        repository.addProperty(newProperty1)
        val testid1 = repository.allProperty().last().component1()

        val testName2 = "Value_${Random.nextInt(100001)}"
        val valu1 = repository.allValue().lastOrNull()
        var id2 = 0
        if (valu1 != null) {
            id2 = valu1.id + 1
        }
        val newValue1 = Value(id = id2,name = testName2, value = "${Random.nextInt(50,100)}", type = "Int" )
        repository.addValue(newValue1)
        val testid2 = repository.allValue().last().component1()

        val testName3 = "HDT_${Random.nextInt(100001)}"
        val hdt = repository.allHDT().lastOrNull()
        var id3 = 0
        if (hdt != null) {
            id3 = hdt.id + 1
        }
        val newHDT = HumanDigitalTwin(id= id3,name = testName3)
        repository.addHDT(newHDT)
        val testid3 = repository.allHDT().last().component1()

        val testName4 = "Property_${Random.nextInt(100001)}"
        val prop2 = repository.allProperty().lastOrNull()
        var id4 = 0
        if (prop2 != null) {
            id4 = prop2.id + 1
        }
        val newProperty2 = Property(id= id4,name = testName4, description = "Test description")
        repository.addProperty(newProperty2)
        val testid4 = repository.allProperty().last().component1()

        val testName5 = "Value_${Random.nextInt(100001)}"
        val valu2 = repository.allValue().lastOrNull()
        var id5 = 0
        if (valu2 != null) {
            id5 = valu2.id + 1
        }
        val newValue2 = Value(id = id5,name = testName5, value = "${Random.nextInt(10,40)}", type = "Int" )
        repository.addValue(newValue2)
        val testid5 = repository.allValue().last().component1()

        val newImplements1 = Implements(id = Random.nextInt(100001),property_id = testid1, humandigitaltwin_id = testid3)
        val newImplements2 = Implements(id = Random.nextInt(100001),property_id = testid4, humandigitaltwin_id = testid3)
        val newDefines1 = Defines(id = Random.nextInt(100001), property_id = testid1, value_id = testid2)
        val newDefines2 = Defines(id = Random.nextInt(100001), property_id = testid4, value_id = testid5)
        repository.addImplements(newImplements1)
        repository.addImplements(newImplements2)
        repository.addDefines(newDefines1)
        repository.addDefines(newDefines2)
        val dt = repository.dtPropertyRange(testName1 ,"50","100")
        if(dt != null) {
            dt.shouldNotBeEmpty()
            dt.forEach { println("nome HDT : ${it.name}") }
        }else fail("Errore")

    }
    
})