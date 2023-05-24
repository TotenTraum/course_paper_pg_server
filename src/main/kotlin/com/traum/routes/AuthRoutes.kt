package com.traum.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.traum.JWTOptions
import com.traum.RepositoryInjector
import com.traum.dtos.auth.LoginDTO
import com.traum.repositories.interfaces.IAuthRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.auth() {
    post("/login") {
        val dto = call.receive<LoginDTO>()
        val connection = RepositoryInjector.factory.createConnection(dto.username, dto.password)
        if (!connection.isValid(100))
            call.respond(HttpStatusCode.BadRequest)
        else {
            val token = JWT.create()
                .withAudience(JWTOptions.audience)
                .withIssuer(JWTOptions.issuer)
                .withClaim("username", dto.username)
                .withClaim("password", dto.password)
                .withExpiresAt(Date(System.currentTimeMillis() + 60000))
                .sign(Algorithm.HMAC256(JWTOptions.secret))
            call.respond(hashMapOf("token" to token))
        }
    }

    authenticate {
        get("/groups") {
            val repository = RepositoryInjector.getRepository<IAuthRepository>(call)
            call.respond(repository.getCurrentGroup())
        }
    }
}