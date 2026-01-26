import io.github.whdt.configureSerialization
import io.github.whdt.core.hdt.HumanDigitalTwin
import io.github.whdt.core.hdt.interfaces.digital.MqttDigitalInterface
import io.github.whdt.core.hdt.interfaces.physical.MqttPhysicalInterface
import io.github.whdt.core.hdt.model.Model
import io.github.whdt.core.hdt.model.id.HdtId
import io.github.whdt.core.hdt.model.property.Property
import io.github.whdt.core.hdt.model.property.PropertyValue
import io.github.whdt.core.serde.Stub
import io.github.whdt.db.configureDatabaseSchema
import io.github.whdt.db.configureDatabases
import io.github.whdt.db.repository.PostgresHDTRepository
import io.kotest.common.runBlocking
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAnyOf
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation as ClientContentNegotiation
import io.ktor.server.testing.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import model.DomainCommand
import model.GetDtPropertyRange

class ApplicationTest : FunSpec({

    lateinit var db : PostgresHDTRepository
    val commandChannel = Channel<DomainCommand>(1000)

    // 2. Definiamo un helper per non ripetere la config
    fun withHdtApp(testBlock: suspend ApplicationTestBuilder.() -> Unit) {
        testApplication {
            application {
                configureDatabases()
                runBlocking { configureDatabaseSchema() }
                configureSerialization()
                configureDatabaseWorker(commandChannel)
                configureRouting(commandChannel)
            }
            testBlock()
        }
    }


    test("Create HDT ") {
        withHdtApp {

            val nameValue = PropertyValue.StringPropertyValue("John")
            val surnameValue = PropertyValue.StringPropertyValue("Doe")
            val hrValue = PropertyValue.IntPropertyValue(72)

            val propName = Property(
                name = "firstName",
                id = "prop-fname",
                description = "Nome dell'utente",
                valueMap = mapOf("val" to nameValue)
            )

            val propSurname = Property(
                name = "surname",
                id = "prop-sname",
                description = "Cognome dell'utente",
                valueMap = mapOf("val" to surnameValue)
            )

            val propHr = Property(
                name = "heartRate",
                id = "prop-hr",
                description = "Battito cardiaco",
                valueMap = mapOf("bpm" to hrValue)
            )

            val model = Model(properties = listOf(propName, propSurname, propHr))

            val pI = MqttPhysicalInterface(
                broker = "localhost",
                port = 1883,
                id = "phy-smartwatch",
                hdtId = HdtId("hdt-mario-rossi-001")

            )

            val dI = MqttDigitalInterface(
                broker = "localhost",
                port = 1883,
                id = "dig-dashboard",
                hdtId = HdtId("hdt-mario-rossi-001")
            )

            val hdt = HumanDigitalTwin(
                hdtId = HdtId("hdt-mario-rossi-001"),
                models = listOf(model),
                physicalInterfaces = listOf(pI),
                digitalInterfaces = listOf(dI),
            )

            val jsonPayload = Stub.hdtJsonSerDe().serialize(hdt)

            val response = client.post("/hdt/create/hdt") {
                contentType(ContentType.Application.Json)
                setBody(jsonPayload)
            }

            response.status shouldBe HttpStatusCode.OK
        }
    }

    test("Create Property") {
        withHdtApp {

            val jsonClient = createClient {
                install(ClientContentNegotiation) {
                    json(Stub.hdtJson)
                }
            }

            val id = HdtId("hdt-mario-rossi-001")
            val propAlt = Property(
                name = "Altezza",
                id = "prop-Altezza",
                description = "altezza",
                valueMap = mapOf("val" to PropertyValue.IntPropertyValue(172))
            )

            val response = jsonClient.post("/hdt/create/property") {
                contentType(ContentType.Application.Json)
                setBody(
                    CreatePropertyRequest(
                        hdt = id,
                        prop = propAlt
                    )
                )
            }

            response.status shouldBe HttpStatusCode.OK
        }
    }
    test("GetDtPropertyRange restituisce la lista degli HDT nel range") {
        withHdtApp {
            val client = createClient {
                install(ClientContentNegotiation) {
                    json(Stub.hdtJson)
                }
            }

            val targetHdtId = HdtId("hdt-target-ok")

            val propTarget = Property(
                name = "temperatura",
                id = "prop-temp-ok",
                description = "Temp",
                valueMap = mapOf("val" to PropertyValue.IntPropertyValue(25))
            )

            val model = Model(properties = listOf(propTarget))

            val hdt = HumanDigitalTwin(
                hdtId = targetHdtId,
                models =  listOf(model),
                physicalInterfaces = emptyList(),
                digitalInterfaces = emptyList()
            )
            client.post("/hdt/create/hdt") {
                contentType(ContentType.Application.Json)
                setBody(Stub.hdtJsonSerDe().serialize(hdt))
            }.status shouldBe HttpStatusCode.OK


            val response = client.get("/hdt/giveBack/GetDtPropertyRange/temperatura/10/30")

            response.status shouldBe HttpStatusCode.OK

            val resultList = response.body<List<io.github.whdt.db.entities.HumanDigitalTwin>>()

            resultList.shouldNotBeEmpty()
            resultList.size shouldBe 1
            resultList[0].name shouldBe "hdt-target-ok"
        }
    }
    test("GetPropertyOfDt restituisce la lista di proprietà del db") {
        withHdtApp {
            val client = createClient {
                install(ClientContentNegotiation) {
                    json(Stub.hdtJson)
                }
            }

            val response = client.get("/hdt/giveBack/handleGetPropertyOfDt/Mario_Rossi")

            response.status shouldBe HttpStatusCode.OK

            val resultList = response.body<List<io.github.whdt.db.entities.Property>>()

            resultList.size shouldBe 5

            val propertyNames = resultList.map { it.name }

            // Deve contenere le sue proprietà specifiche
            propertyNames shouldContain "Battito_Cardiaco"
            propertyNames shouldContain "Temperatura_Corporea"
            propertyNames shouldContain "Pressione_Sistolica"
            propertyNames shouldContain "Pressione_Diastolica"
            propertyNames shouldContain "Saturazione_Ossigeno"

            propertyNames shouldNotContain "Passi_Giornalieri" // Proprietà di Luca
            propertyNames shouldNotContain "Latitudine_GPS"    // Proprietà di Giulia
        }
    }

    test("GetMaxValue restituisce il massimo valore di Battito per Mario_Rossi ") {
        withHdtApp {
            val client = createClient {
                install(ClientContentNegotiation) {
                    json()
                }
            }

            val dataFine = "2026-01-15T12:00:00"
            val dataInizio = "2024-12-20T12:00:00"

            val url = "/hdt/giveBack/GetMaxValue/Mario_Rossi/Battito_Cardiaco/$dataInizio/$dataFine"

            val response = client.get(url)

            response.status shouldBe HttpStatusCode.OK

            val result = response.body<io.github.whdt.db.entities.Value>()


            result.name shouldBe "Battito_Cardiaco"
            result.type shouldBe "int"

            result.value.toInt() shouldBe 100
        }
    }

    test("GetMinValue restituisce il minimo valore di Battito per Mario_Rossi ") {
        withHdtApp {
            val client = createClient {
                install(ClientContentNegotiation) {
                    json()
                }
            }

            val dataFine = "2026-01-15T12:00:00"
            val dataInizio = "2024-12-20T12:00:00"

            val url = "/hdt/giveBack/GetMinValue/Mario_Rossi/Battito_Cardiaco/$dataInizio/$dataFine"

            val response = client.get(url)

            response.status shouldBe HttpStatusCode.OK

            val result = response.body<io.github.whdt.db.entities.Value>()


            result.name shouldBe "Battito_Cardiaco"
            result.type shouldBe "int"

            result.value.toInt() shouldBe 5
        }
    }

    test("GetAvgValue restituisce il minimo valore di Battito per Mario_Rossi ") {
        withHdtApp {
            val client = createClient {
                install(ClientContentNegotiation) {
                    json()
                }
            }

            val dataFine = "2026-01-15T12:00:00"
            val dataInizio = "2024-12-20T12:00:00"

            val url = "/hdt/giveBack/GetAvgValue/Mario_Rossi/Battito_Cardiaco/$dataInizio/$dataFine"

            val response = client.get(url)

            response.status shouldBe HttpStatusCode.OK

            val result = response.body<Double>()

            result shouldBe (49.9 plusOrMinus 0.1)
        }
    }

    test("GetValueOfTime restituisce una lista di valori se l'intervallo è valido") {
        withHdtApp {
            val client = createClient {
                install(ClientContentNegotiation) { json() }
            }

            val timeLess = "2026-01-11T12:00:00"
            val timeGreater = "2025-12-01T12:00:00"

            val url = "/hdt/giveBack/GetValueOfTime/$timeLess/$timeGreater"
            val response = client.get(url)

            response.status shouldBe HttpStatusCode.OK

            val resultList = response.body<List<io.github.whdt.db.entities.Value>>()

            resultList.shouldNotBeEmpty()

            println("Trovati ${resultList.size} valori nell'intervallo temporale.")
            resultList.size shouldBe 480
        }
    }
})

/*Script da usare sul db per dati di test

*/
