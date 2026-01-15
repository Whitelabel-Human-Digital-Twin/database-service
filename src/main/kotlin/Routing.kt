package io.github.whdt

import io.github.whdt.model.DomainCommand
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.channels.Channel

fun Application.configureRouting(commands: Channel<DomainCommand>) {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }
}
