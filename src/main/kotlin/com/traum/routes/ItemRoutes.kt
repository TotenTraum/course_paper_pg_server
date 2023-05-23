package com.traum.routes

import com.traum.RepositoryInjector
import com.traum.adapt
import com.traum.dtos.item.GetItemDTO
import com.traum.dtos.item.PatchItemDTO
import com.traum.dtos.item.PostItemDTO
import com.traum.models.Item
import com.traum.receiveAndAdapt
import com.traum.repositories.interfaces.IItemRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.items() {
    get("/items") {
        val repository = RepositoryInjector.getRepository<IItemRepository>(call)
        call.respond(repository.getAll().map {
            it.adapt<Item, GetItemDTO>()
        })
    }

    delete("/items/{id}") {
        val repository = RepositoryInjector.getRepository<IItemRepository>(call)
        val id = call.parameters["id"]?.toLong()
        repository.delete(id!!)
        call.respond(HttpStatusCode.OK)
    }

    get("/items/{id}") {
        val repository = RepositoryInjector.getRepository<IItemRepository>(call)
        val id = call.parameters["id"]?.toLong()
        val itemDTO = repository.getById(id!!).adapt<Item, GetItemDTO>()
        call.respond(itemDTO)
    }

    patch("/items/{id}") {
        val repository = RepositoryInjector.getRepository<IItemRepository>(call)
        val id = call.parameters["id"]?.toLong()
        val item = repository.getById(id!!)
        call.receive<PatchItemDTO>().adapt(item)
        repository.update(item)
        call.respond(HttpStatusCode.OK)
    }

    post("/items") {
        val item = call.receiveAndAdapt<PostItemDTO, Item>()
        val repository = RepositoryInjector.getRepository<IItemRepository>(call)
        val id: Long = repository.add(item)
        call.respond(id)
    }
}