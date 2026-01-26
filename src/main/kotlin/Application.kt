package io.github.whdt

import configureDatabaseWorker
import configureRouting
import io.github.whdt.db.configureDatabaseSchema
import io.github.whdt.db.configureDatabases
import io.ktor.server.application.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import model.DomainCommand

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    val commandChannel = Channel<DomainCommand>(1000)

    configureHTTP()
    configureSerialization()
    configureDatabases()
    launch { configureDatabaseSchema() }
    configureRouting(commandChannel)
    //configureMqttListener(commandChannel)
    configureDatabaseWorker(commandChannel)
}


