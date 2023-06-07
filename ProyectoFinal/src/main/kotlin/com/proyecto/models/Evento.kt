package com.proyecto.models

import com.proyecto.serializer.LocalDateTimeSerializer
import com.proyecto.serializer.ObjectIdSerializer
import kotlinx.serialization.Serializable
import org.bson.BsonObjectId
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.LocalDateTime

@Serializable
data class Evento(
    @BsonId
    @Serializable(with = ObjectIdSerializer::class)
    val id: ObjectId,
    var nombre: String,
    var descripcion: String,
    @Serializable(with =LocalDateTimeSerializer::class)
    var fecha: LocalDateTime,
    var lugar: String,
    var imagen: String?,
    var desafios: List<Desafio>,
    var ranking: List<Ranking>
)
