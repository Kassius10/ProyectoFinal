package com.proyecto.validators

import com.proyecto.dto.CreateEvento
import io.ktor.server.plugins.requestvalidation.*

fun RequestValidationConfig.eventoValidation(){
    validate<CreateEvento> { evento ->
        if (evento.name.isBlank()){
            ValidationResult.Invalid("El nombre no puede estar vacio.")
        } else if (evento.lugar.isBlank()){
            ValidationResult.Invalid("El lugar no puede estar vacio.")
        }
        else{
            ValidationResult.Valid
        }
    }
}