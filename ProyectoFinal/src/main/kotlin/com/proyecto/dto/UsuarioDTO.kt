package com.proyecto.dto

import joseluisgs.es.serializers.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class CreateUsuario(
    var userName: String,
    var name: String,
    var surname: String,
    var password: String,
    @Serializable(with = LocalDateSerializer::class)
    var dateBirth: LocalDate
)
@Serializable
data class UsuarioDTO(
    var userName: String,
    var name: String,
    var surname: String
)