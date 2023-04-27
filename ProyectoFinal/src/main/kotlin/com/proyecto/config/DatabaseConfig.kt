package com.proyecto.config

import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Single

@Single
data class DatabaseConfig(
    @InjectedParam private val config: Map<String, String>
){
    val uri = config["uri"].toString()
    val database = config["database"].toString()
}
