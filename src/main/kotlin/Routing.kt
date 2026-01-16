import io.github.whdt.core.hdt.HumanDigitalTwin
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.channels.Channel
import model.DomainCommand
import model.InsertDigitalTwin
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun Application.configureRouting(commands: Channel<DomainCommand>) {
    routing {

        post("/hdt/create") {
            val hdt = call.receive<HumanDigitalTwin>()
            val reply = CompletableDeferred<Unit>()
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
    }
}
