package com.proyecto.plugins

import com.proyecto.validators.eventoValidation
import com.proyecto.validators.usuarioValidation
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configureValidation(){
    install(RequestValidation){
        eventoValidation()
        usuarioValidation()
    }
}