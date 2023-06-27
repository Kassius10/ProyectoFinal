package com.proyecto.plugins

import com.proyecto.config.TokenConfig
import com.proyecto.services.TokenService
import io.ktor.http.*

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.get
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {

    val tokenConfigParams = mapOf(
        "audience" to environment.config.property("jwt.audience").getString(),
        "secret" to environment.config.property("jwt.secret").getString(),
        "issuer" to environment.config.property("jwt.issuer").getString(),
        "realm" to environment.config.property("jwt.realm").getString(),
        "expiration" to environment.config.property("jwt.expiration").getString()
    )

    val tokenConfig: TokenConfig = get{ parametersOf(tokenConfigParams) }

    val jwtService: TokenService by inject()

    authentication {
        jwt {
            verifier(jwtService.verify())
            realm = tokenConfig.realm

            validate { credentials ->
                if (credentials.payload.audience.contains(tokenConfig.audience) &&
                    credentials.payload.getClaim("username").asString().isNotEmpty()){
                    JWTPrincipal(credentials.payload)
                }
                else null
            }
            challenge{_,_->
                call.respond(HttpStatusCode.Unauthorized, "Token invalido o expirado")
            }
        }
    }

}