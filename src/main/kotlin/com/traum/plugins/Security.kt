package com.traum.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.traum.JWTOptions
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity() {
    JWTOptions.audience = environment.config.property("jwt.audience").getString()
    JWTOptions.secret = environment.config.property("jwt.secret").getString()
    JWTOptions.myRealm = environment.config.property("jwt.realm").getString()
    JWTOptions.issuer = environment.config.property("jwt.domain").getString()
    authentication {
        jwt {
            realm = JWTOptions.myRealm!!
            verifier(
                JWT
                    .require(Algorithm.HMAC256(JWTOptions.secret))
                    .withAudience(JWTOptions.audience)
                    .withIssuer(JWTOptions.issuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(JWTOptions.audience)) JWTPrincipal(credential.payload) else null
            }
        }
    }
}
