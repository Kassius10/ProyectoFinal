package com.proyecto.dto

import com.proyecto.models.Desafio
import com.proyecto.serializer.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class CreateEvento(
    val name: String,
    val descripcion: String,
    val lugar: String,
    val imagen: String?,
    val desafios: List<Desafio> = mutableListOf()
)
@Serializable
data class EventoDTO(
    val name: String,
    val descripcion: String,
    val lugar: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val fecha: LocalDateTime,
    val imagen: String,
    val desafios: List<Desafio>
)