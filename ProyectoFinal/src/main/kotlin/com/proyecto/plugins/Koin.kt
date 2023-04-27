package com.proyecto.plugins

import io.ktor.server.application.*
import org.koin.ksp.generated.defaultModule
import org.koin.ktor.plugin.Koin

fun Application.configureKoin() {
    install(Koin) {
        defaultModule()
    }
}