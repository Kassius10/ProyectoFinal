package com.proyecto.models

import kotlinx.serialization.Serializable

@Serializable
data class Ranking(
    var userName: String,
    var tiempo: String,
)
