package com.traum.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.traum.JWTOptions
import com.traum.RepositoryInjector
import com.traum.dtos.ErrorDTO
import com.traum.dtos.auth.LoginDTO
import com.traum.repositories.interfaces.IAuthRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.postgresql.util.PSQLException
import java.util.*

fun Route.auth() {
    post("/login") {
        val dto = call.receive<LoginDTO>()
        try {
            val connection = RepositoryInjector.factory.createConnection(dto.username, dto.password)
            if (connection.isValid(100)) {
                val token = JWT.create()
                    .withAudience(JWTOptions.audience)
                    .withIssuer(JWTOptions.issuer)
                    .withClaim("username", dto.username)
                    .withClaim("password", dto.password)
                    .withExpiresAt(Date(System.currentTimeMillis() + 60000))
                    .sign(Algorithm.HMAC256(JWTOptions.secret))
                call.respond(hashMapOf("token" to token))
            }
        } catch (exception: PSQLException) {
            this.application.log.info(exception.toString())
            call.respond(HttpStatusCode.Forbidden, ErrorDTO("Проблемы авторизации пользователя"))
        }
    }

    authenticate {
        get("/groups") {
            val repository = RepositoryInjector.getRepository<IAuthRepository>(call)
            call.respond(repository.getCurrentGroup())
        }

        get("/is-admin") {
            val repository = RepositoryInjector.getRepository<IAuthRepository>(call)
            val isAdmin = repository.getCurrentGroup().filter { it == "admins" }.any()
            call.respond(hashMapOf("isAdmin" to isAdmin))
        }
    }
}