package com.proyecto.validators

import com.proyecto.dto.CreateUsuario
import com.proyecto.dto.UpdateUsuario
import com.proyecto.models.Usuario
import io.ktor.server.plugins.requestvalidation.*
import java.time.LocalDate

fun RequestValidationConfig.usuarioValidation(){
    validate<CreateUsuario> { usuario ->
        if (usuario.userName.isBlank()){
            ValidationResult.Invalid("El nombre de usuario no puede estar vacío.")
        } else if (usuario.name.isBlank()){
            ValidationResult.Invalid("El nombre no puede estar vacío.")
        } else if (usuario.userName.isBlank()){
            ValidationResult.Invalid("Los apellidos no pueden estar vacío.")
        } else if (usuario.password.length < 8){
            ValidationResult.Invalid("La contraseña no puede ser menor de 8 digitos.")
        } else if (usuario.dateBirth.isAfter(LocalDate.now())){
            ValidationResult.Invalid("La fecha no puede ser superior a la fecha actual.")
        } else if (usuario.rol != Usuario.Rol.USER && usuario.rol != Usuario.Rol.ADMIN) {
            ValidationResult.Invalid("El rol es inválido")
        }else{
            ValidationResult.Valid
        }
    }

    validate<UpdateUsuario> { usuario ->
        if (usuario.userName.isBlank()){
            ValidationResult.Invalid("El nombre de usuario no puede estar vacío.")
        } else if (usuario.name.isBlank()){
            ValidationResult.Invalid("El nombre no puede estar vacío.")
        } else if (usuario.userName.isBlank()){
            ValidationResult.Invalid("Los apellidos no pueden estar vacío.")
        } else if (usuario.dateBirth.isAfter(LocalDate.now())){
            ValidationResult.Invalid("La fecha no puede ser superior a la fecha actual.")
        } else{
            ValidationResult.Valid
        }
    }
}