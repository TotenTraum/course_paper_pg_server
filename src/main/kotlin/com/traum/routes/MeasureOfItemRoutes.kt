package com.traum.routes

import com.traum.RepositoryInjector
import com.traum.adapt
import com.traum.dtos.measure_of_item.GetMeasureOfItemDTO
import com.traum.dtos.measure_of_item.PatchMeasureOfItemDTO
import com.traum.dtos.measure_of_item.PostMeasureOfItemDTO
import com.traum.models.MeasureOfItem
import com.traum.receiveAndAdapt
import com.traum.repositories.interfaces.IMeasureOfItemRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.measuresOfItem() {
    get("/items/{itemId}/measures") {
        val repository = RepositoryInjector.getRepository<IMeasureOfItemRepository>(call)
        val itemId = call.parameters["itemId"]?.toLong()!!
        call.respond(repository.getAll(itemId).map {
            it.adapt<MeasureOfItem, GetMeasureOfItemDTO>()
        })
    }

    delete("/items/{itemId}/measures/{id}") {
        val repository = RepositoryInjector.getRepository<IMeasureOfItemRepository>(call)
        val id = call.parameters["id"]?.toLong()
        repository.delete(id!!)
        call.respond(HttpStatusCode.OK)
    }

    get("/items/{itemId}/measures/{id}") {
        val repository = RepositoryInjector.getRepository<IMeasureOfItemRepository>(call)
        val id = call.parameters["id"]?.toLong()!!
        val measureOfItemDTO = repository.getById(id).adapt<MeasureOfItem, GetMeasureOfItemDTO>()
        call.respond(measureOfItemDTO)
    }

    patch("/items/{itemId}/measures/{id}") {
        val repository = RepositoryInjector.getRepository<IMeasureOfItemRepository>(call)
        val id = call.parameters["id"]?.toLong()
        val measureOfItem = repository.getById(id!!)
        call.receive<PatchMeasureOfItemDTO>().adapt(measureOfItem)
        repository.update(measureOfItem)
        call.respond(HttpStatusCode.OK)
    }

    post("/items/{itemId}/measures") {
        val measureOfItem = call.receiveAndAdapt<PostMeasureOfItemDTO, MeasureOfItem>()
        measureOfItem.itemId = call.parameters["itemId"]?.toLong()!!
        val repository = RepositoryInjector.getRepository<IMeasureOfItemRepository>(call)
        val id: Long = repository.add(measureOfItem)
        call.respond(id)
    }
}