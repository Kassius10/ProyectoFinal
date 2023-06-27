package com.proyecto.models

import kotlinx.serialization.Serializable

@Serializable
data class Desafio(
    val numDesafio: Int,
    val nombreDesafio: String,
    val descripcion: String,
    val direccion: Direccion,
    val resultado: String,
    val clave: String
)
@Serializable
data class Direccion(
    val latitud: String,
    val longitud: String
)
