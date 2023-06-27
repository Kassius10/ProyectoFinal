package com.proyecto.validators

import com.proyecto.dto.CreateEvento
import io.ktor.server.plugins.requestvalidation.*
import java.time.LocalDateTime

fun RequestValidationConfig.eventoValidation(){
    validate<CreateEvento> { evento ->
        if (evento.nombre.isBlank()){
            ValidationResult.Invalid("El nombre no puede estar vacio.")
        } else if (evento.lugar.isBlank()){
            ValidationResult.Invalid("El lugar no puede estar vacio.")
        } else if (evento.descripcion.isBlank()){
            ValidationResult.Invalid("La descripción no puede estar vacia.")
        } else if (evento.fecha.isBefore(LocalDateTime.now())){
            ValidationResult.Invalid("El evento no puede ser anterior a hoy.")
        }else{
            ValidationResult.Valid
        }
    }
}