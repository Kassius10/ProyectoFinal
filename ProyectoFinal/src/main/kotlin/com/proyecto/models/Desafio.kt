package com.proyecto.models

import com.proyecto.serializer.ObjectIdSerializer
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

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
