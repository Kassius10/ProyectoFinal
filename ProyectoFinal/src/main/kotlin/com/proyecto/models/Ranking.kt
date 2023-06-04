package com.proyecto.models

import com.proyecto.serializer.ObjectIdSerializer
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
@Serializable
data class Ranking(
    @BsonId
    @Serializable(with = ObjectIdSerializer::class)
    val id: ObjectId,
    val eventoId: String,
    var rankingUsers: List<RankingUser>
)
