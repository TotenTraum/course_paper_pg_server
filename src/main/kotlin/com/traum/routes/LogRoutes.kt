package com.traum.routes

import com.traum.RepositoryInjector
import com.traum.adapt
import com.traum.dtos.log.GetLogDTO
import com.traum.models.Log
import com.traum.repositories.interfaces.ILogRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.logs() {
    get("/logs") {
        val repository = RepositoryInjector.getRepository<ILogRepository>(call)
        call.respond(repository.getAll().map {
            it.adapt<Log, GetLogDTO>()
        })
    }
}