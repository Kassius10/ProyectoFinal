package com.proyecto.dto

import com.proyecto.models.Desafio
import com.proyecto.models.Ranking
import com.proyecto.serializer.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class CreateEvento(
    val nombre: String,
    val descripcion: String,
    val lugar: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val fecha: LocalDateTime,
    val imagen: String?,
    val desafios: MutableList<Desafio> = mutableListOf(),
    val ranking: MutableList<Ranking> = mutableListOf()
)

@Serializable
data class UpdateEvento(
    val nombre: String,
    val descripcion: String,
    val lugar: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val fecha: LocalDateTime,
    val imagen: String?,
    val desafios: MutableList<Desafio> = mutableListOf()
)
@Serializable
data class EventoDTO(
    val nombre: String,
    val descripcion: String,
    val lugar: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val fecha: LocalDateTime,
    val imagen: String,
    val desafios: MutableList<Desafio>,
    val ranking: MutableList<Ranking>
)