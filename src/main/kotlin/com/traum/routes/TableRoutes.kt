package com.traum.routes

import com.traum.RepositoryInjector
import com.traum.adapt
import com.traum.dtos.table.GetTableDTO
import com.traum.dtos.table.PatchTableDTO
import com.traum.dtos.table.PostTableDTO
import com.traum.models.Table
import com.traum.receiveAndAdapt
import com.traum.repositories.interfaces.ITableRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.tables() {
    get("/tables") {
        val repository = RepositoryInjector.getRepository<ITableRepository>(call)
        call.respond(repository.getAll().map {
            it.adapt<Table, GetTableDTO>()
        })
    }

    delete("/tables/{id}") {
        val repository = RepositoryInjector.getRepository<ITableRepository>(call)
        val id = call.parameters["id"]?.toLong()
        repository.delete(id!!)
        call.respond(HttpStatusCode.OK)
    }

    get("/tables/{id}") {
        val repository = RepositoryInjector.getRepository<ITableRepository>(call)
        val id = call.parameters["id"]?.toLong()
        call.respond(repository.getById(id!!).adapt<Table, GetTableDTO>())
    }

    patch("/tables/{id}") {
        val repository = RepositoryInjector.getRepository<ITableRepository>(call)
        val id = call.parameters["id"]?.toLong()
        val table = repository.getById(id!!)
        call.receive<PatchTableDTO>().adapt(table)
        repository.update(table)
        call.respond(HttpStatusCode.OK)
    }

    post("/tables") {
        val table = call.receiveAndAdapt<PostTableDTO, Table>()
        val repository = RepositoryInjector.getRepository<ITableRepository>(call)
        val id: Long = repository.add(table)
        call.respond(id)
    }
}