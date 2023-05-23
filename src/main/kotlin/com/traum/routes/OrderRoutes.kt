package com.traum.routes

import com.traum.RepositoryInjector
import com.traum.adapt
import com.traum.dtos.order.GetOrderDTO
import com.traum.dtos.order.PatchOrderDTO
import com.traum.dtos.order.PostOrderDTO
import com.traum.models.Order
import com.traum.receiveAndAdapt
import com.traum.repositories.interfaces.IOrderRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.orders() {
    get("/orders") {
        val repository = RepositoryInjector.getRepository<IOrderRepository>(call)
        call.respond(repository.getAll().map {
            it.adapt<Order, GetOrderDTO>()
        })
    }

    delete("/orders/{id}") {
        val repository = RepositoryInjector.getRepository<IOrderRepository>(call)
        val id = call.parameters["id"]?.toLong()
        repository.delete(id!!)
        call.respond(HttpStatusCode.OK)
    }

    get("/orders/{id}") {
        val repository = RepositoryInjector.getRepository<IOrderRepository>(call)
        val id = call.parameters["id"]?.toLong()
        val order = repository.getById(id!!).adapt<Order, GetOrderDTO>()
        call.respond(order)
    }

    patch("/orders/{id}") {
        val repository = RepositoryInjector.getRepository<IOrderRepository>(call)
        val id = call.parameters["id"]?.toLong()
        val order = repository.getById(id!!)
        call.receive<PatchOrderDTO>().adapt(order)
        repository.update(order)
        call.respond(HttpStatusCode.OK)
    }

    /**
     * Создание заказа
     */
    post("/orders") {
        val order = call.receiveAndAdapt<PostOrderDTO, Order>()
        val repository = RepositoryInjector.getRepository<IOrderRepository>(call)
        val id: Long = repository.add(order)
        call.respond(id)
    }
}