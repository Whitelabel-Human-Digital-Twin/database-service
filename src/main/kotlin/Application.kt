package io.github.whdt

import io.github.whdt.db.configureDatabases
import io.github.whdt.model.DomainCommand
import io.ktor.server.application.*
import kotlinx.coroutines.channels.Channel

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val commandChannel = Channel<DomainCommand>(1000)

    configureHTTP()
    configureSerialization()
    configureDatabases()
    configureRouting(commandChannel)
    configureMqttListener(commandChannel)
    configureDatabaseWorker(commandChannel)
}


