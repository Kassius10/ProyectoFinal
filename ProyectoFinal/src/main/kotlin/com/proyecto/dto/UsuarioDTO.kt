package com.proyecto.dto

import com.proyecto.models.Usuario
import com.proyecto.serializer.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class CreateUsuario(
    var userName: String,
    var name: String,
    var surname: String,
    var email: String,
    var password: String,
    @Serializable(with = LocalDateSerializer::class)
    var dateBirth: LocalDate,
    val rol : Usuario.Rol = Usuario.Rol.USER
)
@Serializable
data class UpdateUsuario(
    var userName: String,
    var name: String,
    var surname: String,
    var email: String,
    @Serializable(with = LocalDateSerializer::class)
    var dateBirth: LocalDate,
)

@Serializable
data class UsuarioDTO(
    var userName: String,
    var name: String,
    var surname: String,
    var email: String,
    @Serializable(with = LocalDateSerializer::class)
    var dateBirth: LocalDate,
)

@Serializable
data class UserLogin(
    val userName: String,
    val password: String
)

@Serializable
data class UsuarioDTOWithToken(
    var id: String,
    var userName: String,
    var name: String,
    var surname: String,
    var email: String,
    var token: String,
    @Serializable(with = LocalDateSerializer::class)
    var dateBirth: LocalDate
)