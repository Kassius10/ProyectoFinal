package com.proyecto.models

import kotlinx.serialization.Serializable

@Serializable
data class RankingUser(
    var posicion: Int,
    var userId: String,
    var tiempo: String?,
)
