package io.github.whdt

import io.github.whdt.model.DomainCommand
import io.github.whdt.model.NotifyFailureCommand
import io.github.whdt.model.ReadProperty
import io.github.whdt.model.UpdateProperty
import io.ktor.server.application.Application
import io.ktor.server.application.log
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

fun Application.configureDatabaseWorker(commandChannel: Channel<DomainCommand>) {
    launch {
        for (cmd in commandChannel) {
            when (cmd) {
                is UpdateProperty -> handleUpdateProperty(cmd)
                is ReadProperty -> handleReadProperty(cmd)
                is NotifyFailureCommand -> log.warn("Failed: ${cmd.reason}")
            }
        }
    }
}

fun handleUpdateProperty(cmd: UpdateProperty) {
    // TODO CALL DB SUSPEND TRANSACTION HERE
}

fun handleReadProperty(cmd: ReadProperty) {
    // TODO CALL DB SUSPEND TRANSACTION HERE
}