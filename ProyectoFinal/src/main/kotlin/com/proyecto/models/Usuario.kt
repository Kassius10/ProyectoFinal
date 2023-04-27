package com.proyecto.models

import com.proyecto.serializer.ObjectIdSerializer
import joseluisgs.es.serializers.LocalDateSerializer
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.LocalDate

@Serializable
data class Usuario(
    @BsonId
    @Serializable(with = ObjectIdSerializer::class)
    val id: ObjectId,
    var userName: String,
    var name: String,
    var surname: String,
    var password: String,
    @Serializable(with = LocalDateSerializer::class)
    var dateBirth: LocalDate
)
