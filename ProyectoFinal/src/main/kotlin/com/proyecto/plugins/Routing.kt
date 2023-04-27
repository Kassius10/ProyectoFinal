package com.proyecto.plugins

import com.proyecto.routes.eventoRoutes
import com.proyecto.routes.usuarioRoutes
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }
    eventoRoutes()
    usuarioRoutes()
}
