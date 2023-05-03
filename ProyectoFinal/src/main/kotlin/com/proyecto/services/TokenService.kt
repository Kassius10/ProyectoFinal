package com.proyecto.services

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.proyecto.config.TokenConfig
import com.proyecto.models.Usuario
import org.koin.core.annotation.Single
import java.util.*

@Single
class TokenService(
    private val tokenConfig: TokenConfig
) {
    fun generateJWT(user:Usuario):String{
        return JWT.create()
            .withAudience(tokenConfig.audience)
            .withIssuer(tokenConfig.issuer)
            .withSubject("Authentication")
            .withClaim("username",user.userName)
            .withExpiresAt(Date(System.currentTimeMillis() + tokenConfig.expiration * 1000))
            .sign(Algorithm.HMAC512(tokenConfig.secret))
    }

    fun verify(): JWTVerifier{
        return JWT.require(Algorithm.HMAC512(tokenConfig.secret))
            .withAudience(tokenConfig.audience)
            .withIssuer(tokenConfig.issuer)
            .build()
    }

}