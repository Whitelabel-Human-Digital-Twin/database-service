import io.ktor.server.application.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import model.*

fun Application.configureDatabaseWorker(commandChannel: Channel<DomainCommand>) {
    launch {
        for (cmd in commandChannel) {
            when (cmd) {
                is InsertDigitalTwin -> TODO()
                is InsertDigitalTwinBatch -> TODO()
                is InsertProperty -> TODO()
                is NotifyFailureCommand -> log.warn("Failed: ${cmd.reason}")
            }
        }
    }
}
