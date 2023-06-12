package com.proyecto

import io.ktor.server.application.*
import com.proyecto.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureKoin()
    configureSecurity()
    configureSockets()
    configureHTTP()

    configureSerialization()
    configureRouting()
    configureValidation()
    configureSwagger()
}
