import io.github.whdt.core.hdt.HumanDigitalTwin
import io.github.whdt.db.entities.Value
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.channels.Channel
import kotlinx.datetime.LocalDateTime
import model.DomainCommand
import model.GetAvgValue
import model.GetDtPropertyRange
import model.GetMaxValue
import model.GetMinValue
import model.GetPropertyOfDt
import model.GetValueOfTime
import model.GetValueOfTimeDt
import model.GetValueOfTimeOfPropOfDt
import model.InsertDigitalTwin
import model.InsertProperty
import model.InsertTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun Application.configureRouting(commands: Channel<DomainCommand>) {
    routing {

        // testata
        post("/hdt/create/hdt") {
            val hdt = call.receive<HumanDigitalTwin>()
            val reply = CompletableDeferred<Boolean>()
            val command = InsertDigitalTwin(hdt, Clock.System.now(), reply)
            val result = commands.trySend(command)
            result.also {
                if(it.isSuccess) {
                    call.respond(reply.await())
                } else {
                    call.respond(HttpStatusCode.InternalServerError)
                }
            }
        }

        // testata
        post("/hdt/create/property") {
            val tmp = call.receive<CreatePropertyRequest>()
            val reply = CompletableDeferred<Boolean>()
            val command = InsertProperty(tmp.hdt, tmp.prop,Clock.System.now(), reply)
            val result = commands.trySend(command)
            result.also {
                if(it.isSuccess) {
                    call.respond(reply.await())
                } else {
                    call.respond(HttpStatusCode.InternalServerError)
                }
            }
        }

        post("/"){
            val tmp = call.receive<CreateTimeRequest>()
            val reply = CompletableDeferred<Boolean>()
            val command = InsertTime(tmp.hdt, tmp.prop, tmp.valu, tmp.time, Clock.System.now(), reply)
            val result = commands.trySend(command)
            result.also {
                if(it.isSuccess) {
                    call.respond(reply.await())
                } else {
                    call.respond(HttpStatusCode.InternalServerError)
                }
            }
        }

        // testata
        get("/hdt/giveBack/handleGetPropertyOfDt/{hdtId}") {
            val hdt = call.parameters["hdtId"]
            if (hdt == null ) {
                call.respond(HttpStatusCode.BadRequest, "Manca il parametro")
                return@get
            }
            val reply = CompletableDeferred<Any>()
            val command = GetPropertyOfDt(hdt, Clock.System.now(), reply)
            val result = commands.trySend(command)
            result.also {
                if (it.isSuccess) {
                    when (val rawData = reply.await()) {
                        false ->{
                            call.respond(HttpStatusCode.NotFound)
                        }
                        is List<*> ->{
                            @Suppress("UNCHECKED_CAST")
                            call.respond(rawData as List<io.github.whdt.db.entities.Property>)
                        }
                    }
                } else {
                    call.respond(HttpStatusCode.InternalServerError)
                }
            }
        }

        // testata
        get("/hdt/giveBack/GetMaxValue/{hdtId}/{propertyName}/{timeLess}/{TimeGreter}") {
            val hdt = call.parameters["hdtId"]
            val propertyName = call.parameters["propertyName"]
            val timeLess = call.parameters["timeLess"]
            val timeGreter = call.parameters["timeGreter"]
            if (hdt == null || propertyName == null || timeLess == null || timeGreter == null ) {
                call.respond(HttpStatusCode.BadRequest, "Manca il parametro")
                return@get
            }
            val tLess: LocalDateTime
            val tGreater: LocalDateTime
            try {
                tLess = LocalDateTime.parse(timeLess)
                tGreater = LocalDateTime.parse(timeGreter)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Formato data non valido")
                return@get
            }
            val reply = CompletableDeferred<Any>()
            val command = GetMaxValue(hdt,tLess, tGreater,propertyName, Clock.System.now(), reply)
            val result = commands.trySend(command)
            result.also {
                if (it.isSuccess) {
                    when (val rawData = reply.await()) {
                        false ->{
                            call.respond(HttpStatusCode.NotFound)
                        }
                        is Value ->{
                            call.respond(rawData)
                        }
                    }
                } else {
                    call.respond(HttpStatusCode.InternalServerError)
                }
            }
        }

        // testata
        get("/hdt/giveBack/GetMinValue/{hdtId}/{propertyName}/{timeLess}/{TimeGreter}") {
            val hdt = call.parameters["hdtId"]
            val propertyName = call.parameters["propertyName"]
            val timeLess = call.parameters["timeLess"]
            val timeGreter = call.parameters["timeGreter"]
            if (hdt == null || propertyName == null || timeLess == null || timeGreter == null ) {
                call.respond(HttpStatusCode.BadRequest, "Manca il parametro")
                return@get
            }
            val tLess: LocalDateTime
            val tGreater: LocalDateTime
            try {
                tLess = LocalDateTime.parse(timeLess)
                tGreater = LocalDateTime.parse(timeGreter)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Formato data non valido")
                return@get
            }
            val reply = CompletableDeferred<Any>()
            val command = GetMinValue(hdt, tLess, tGreater, propertyName, Clock.System.now(), reply)
            val result = commands.trySend(command)
            result.also {
                if (it.isSuccess) {
                    when (val rawData = reply.await()) {
                        false ->{
                            call.respond(HttpStatusCode.NotFound)
                        }
                        is Value ->{
                            call.respond(rawData)
                        }
                    }
                } else {
                    call.respond(HttpStatusCode.InternalServerError)
                }
            }
        }

        // testata
        get("/hdt/giveBack/GetAvgValue/{hdtId}/{propertyName}/{timeLess}/{TimeGreter}") {
            val hdt = call.parameters["hdtId"]
            val propertyName = call.parameters["propertyName"]
            val timeLess = call.parameters["timeLess"]
            val timeGreter = call.parameters["timeGreter"]
            if (hdt == null || propertyName == null || timeLess == null || timeGreter == null ) {
                call.respond(HttpStatusCode.BadRequest, "Manca il parametro")
                return@get
            }
            val tLess: LocalDateTime
            val tGreater: LocalDateTime
            try {
                tLess = LocalDateTime.parse(timeLess)
                tGreater = LocalDateTime.parse(timeGreter)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Formato data non valido")
                return@get
            }
            val reply = CompletableDeferred<Any>()
            val command = GetAvgValue(hdt, tLess, tGreater, propertyName, Clock.System.now(), reply)
            val result = commands.trySend(command)
            result.also {
                if (it.isSuccess) {
                    when (val rawData = reply.await()) {
                        false ->{
                            call.respond(HttpStatusCode.NotFound)
                        }
                        is Double ->{
                            call.respond(rawData)
                        }
                    }
                } else {
                    call.respond(HttpStatusCode.InternalServerError)
                }
            }
        }

        // testata
        get("/hdt/giveBack/GetDtPropertyRange/{propertyName}/{minValue}/{maxValue}") {
            val propertyName = call.parameters["propertyName"]
            val minValue = call.parameters["minValue"]
            val maxValue = call.parameters["maxValue"]
            if ( propertyName == null || minValue == null || maxValue == null ) {
                call.respond(HttpStatusCode.BadRequest, "Manca il parametro")
                return@get
            }
            val reply = CompletableDeferred<Any>()
            val command = GetDtPropertyRange(propertyName,minValue,maxValue, Clock.System.now(), reply)
            val result = commands.trySend(command)
            result.also {
                if (it.isSuccess) {
                    when (val rawData = reply.await()) {
                        false ->{
                            call.respond(HttpStatusCode.NotFound)
                        }
                        is List<*> ->{
                            @Suppress("UNCHECKED_CAST")
                            call.respond(rawData as List<io.github.whdt.db.entities.HumanDigitalTwin>)
                        }
                    }
                } else {
                    call.respond(HttpStatusCode.InternalServerError)
                }
            }
        }

        get("/hdt/giveBack/GetValueOfTime/{timeLess}/{TimeGreter}") {
            val timeLess = call.parameters["timeLess"]
            val timeGreter = call.parameters["timeGreter"]
            if (timeLess == null || timeGreter == null ) {
                call.respond(HttpStatusCode.BadRequest, "Manca il parametro")
                return@get
            }
            val tLess: LocalDateTime
            val tGreater: LocalDateTime
            try {
                tLess = LocalDateTime.parse(timeLess)
                tGreater = LocalDateTime.parse(timeGreter)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Formato data non valido")
                return@get
            }
            val reply = CompletableDeferred<Any>()
            val command = GetValueOfTime(tLess, tGreater, Clock.System.now(), reply)
            val result = commands.trySend(command)
            result.also {
                if (it.isSuccess) {
                    when (val rawData = reply.await()) {
                        false ->{
                            call.respond(HttpStatusCode.NotFound)
                        }
                        is List<*> ->{
                            @Suppress("UNCHECKED_CAST")
                            call.respond(rawData as List<Value>)
                        }
                    }
                } else {
                    call.respond(HttpStatusCode.InternalServerError)
                }
            }
        }

        get("/hdt/giveBack/GetValueOfTimeOfPropOfDt/{hdtName}/{propertyName}/{timeLess}/{TimeGreter}") {
            val hdt = call.parameters["hdtName"]
            val propertyName = call.parameters["propertyName"]
            val timeLess = call.parameters["timeLess"]
            val timeGreter = call.parameters["timeGreter"]
            if (hdt == null || propertyName == null || timeLess == null || timeGreter == null ) {
                call.respond(HttpStatusCode.BadRequest, "Manca il parametro")
                return@get
            }
            val tLess: LocalDateTime
            val tGreater: LocalDateTime
            try {
                tLess = LocalDateTime.parse(timeLess)
                tGreater = LocalDateTime.parse(timeGreter)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Formato data non valido")
                return@get
            }
            val reply = CompletableDeferred<Any>()
            val command = GetValueOfTimeOfPropOfDt(hdt,propertyName, tLess, tGreater, Clock.System.now(), reply)
            val result = commands.trySend(command)
            result.also {
                if (it.isSuccess) {
                    when (val rawData = reply.await()) {
                        false ->{
                            call.respond(HttpStatusCode.NotFound)
                        }
                        is List<*> ->{
                            @Suppress("UNCHECKED_CAST")
                            call.respond(rawData as List<Value>)
                        }
                    }
                } else {
                    call.respond(HttpStatusCode.InternalServerError)
                }
            }
        }

        get("/hdt/giveBack/GetValueOfTimeDt/{hdtName}/{propertyName}/{timeLess}/{TimeGreter}") {
            val hdt = call.parameters["hdtName"]
            val propertyName = call.parameters["propertyName"]
            val timeLess = call.parameters["timeLess"]
            val timeGreter = call.parameters["timeGreter"]
            if (hdt == null || propertyName == null || timeLess == null || timeGreter == null ) {
                call.respond(HttpStatusCode.BadRequest, "Manca il parametro")
                return@get
            }
            val tLess: LocalDateTime
            val tGreater: LocalDateTime
            try {
                tLess = LocalDateTime.parse(timeLess)
                tGreater = LocalDateTime.parse(timeGreter)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Formato data non valido")
                return@get
            }
            val reply = CompletableDeferred<Any>()
            val command = GetValueOfTimeDt(hdt,  tLess, tGreater, Clock.System.now(), reply)
            val result = commands.trySend(command)
            result.also {
                if (it.isSuccess) {
                    when (val rawData = reply.await()) {
                        false ->{
                            call.respond(HttpStatusCode.NotFound)
                        }
                        is Map<*, *> ->{
                            @Suppress("UNCHECKED_CAST")
                            call.respond(rawData as Map<Int,List<Value>>)
                        }
                    }
                } else {
                    call.respond(HttpStatusCode.InternalServerError)
                }
            }
        }
    }
}
