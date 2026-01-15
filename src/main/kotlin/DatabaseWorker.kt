import io.ktor.server.application.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import model.DomainCommand
import model.NotifyFailureCommand

fun Application.configureDatabaseWorker(commandChannel: Channel<DomainCommand>) {
    launch {
        for (cmd in commandChannel) {
            when (cmd) {
                is NotifyFailureCommand -> log.warn("Failed: ${cmd.reason}")
                else -> {}
            }
        }
    }
}