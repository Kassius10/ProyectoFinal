package com.proyecto.models

import com.proyecto.serializer.LocalDateSerializer
import com.proyecto.serializer.ObjectIdSerializer
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.LocalDate

@Serializable
data class Usuario(
    @BsonId
    @Serializable(with = ObjectIdSerializer::class)
    val id: ObjectId,
    var name: String,
    var surname: String,
    var userName: String,
    var email: String,
    var password: String,
    @Serializable(with = LocalDateSerializer::class)
    var dateBirth: LocalDate
)
