package com.traum.routes

import com.traum.RepositoryInjector
import com.traum.adapt
import com.traum.dtos.element_of_order.GetElementOfOrderDTO
import com.traum.dtos.element_of_order.PatchElementOfOrderDTO
import com.traum.dtos.element_of_order.PostElementOfOrderDTO
import com.traum.models.ElementOfOrder
import com.traum.receiveAndAdapt
import com.traum.repositories.interfaces.IElementOfOrderRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.elementOfOrders() {
    get("/orders/{orderId}/elements") {
        val repository = RepositoryInjector.getRepository<IElementOfOrderRepository>(call)
        call.respond(repository.getAll(call.parameters["orderId"]?.toLong()!!).map {
            it.adapt<ElementOfOrder, GetElementOfOrderDTO>()
        })
    }

    delete("/orders/{orderId}/elements/{id}") {
        val repository = RepositoryInjector.getRepository<IElementOfOrderRepository>(call)
        val id = call.parameters["id"]?.toLong()!!
        val orderId = call.parameters["orderId"]?.toLong()!!
        repository.delete(orderId, id)
        call.respond(HttpStatusCode.OK)
    }

    get("/orders/{orderId}/elements/{id}") {
        val repository = RepositoryInjector.getRepository<IElementOfOrderRepository>(call)
        val id = call.parameters["id"]?.toLong()!!
        val orderId = call.parameters["orderId"]?.toLong()!!

        val measurementDTO = repository.getById(orderId, id)
            .adapt<ElementOfOrder, GetElementOfOrderDTO>()
        call.respond(measurementDTO)
    }

    patch("/orders/{orderId}/elements/{id}") {
        val repository = RepositoryInjector.getRepository<IElementOfOrderRepository>(call)
        val id = call.parameters["id"]?.toLong()!!
        val orderId = call.parameters["orderId"]?.toLong()!!

        val elementOfOrder = repository.getById(orderId, id)
        call.receive<PatchElementOfOrderDTO>().adapt(elementOfOrder)
        repository.update(orderId, elementOfOrder)
        call.respond(HttpStatusCode.OK)
    }

    post("/orders/{orderId}/elements") {
        val elementOfOrder = call.receiveAndAdapt<PostElementOfOrderDTO, ElementOfOrder>()
        val repository =
            RepositoryInjector.getRepository<IElementOfOrderRepository>(call)

        val orderId = call.parameters["orderId"]?.toLong()!!
        val id: Long = repository.add(orderId, elementOfOrder)
        call.respond(id)
    }
}