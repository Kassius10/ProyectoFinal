package com.proyecto.validators

import com.proyecto.models.Ranking
import io.ktor.server.plugins.requestvalidation.*

fun RequestValidationConfig.rankingValidation(){
    validate<Ranking> { ranking ->
        if (ranking.userName.isBlank()){
            ValidationResult.Invalid("El nombre no puede estar vacio.")
        } else if (ranking.tiempo.isBlank()){
            ValidationResult.Invalid("El tiempo es incorrecto.")
        } else{
            ValidationResult.Valid
        }
    }
}