package com.traum.routes

import com.traum.RepositoryInjector
import com.traum.adapt
import com.traum.dtos.price_of_item.GetPriceOfItemDTO
import com.traum.dtos.price_of_item.PostPriceOfItemDTO
import com.traum.models.PriceOfItem
import com.traum.receiveAndAdapt
import com.traum.repositories.interfaces.IPriceOfItemRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.pricesOfItem() {
    get("/items/{itemId}/prices") {
        val repository = RepositoryInjector.getRepository<IPriceOfItemRepository>(call)
        call.respond(repository.getAllByItemId(call.parameters["itemId"]?.toLong()!!).map {
            it.adapt<PriceOfItem, GetPriceOfItemDTO>()
        })
    }

    get("/items/{itemId}/prices/latest") {
        val repository = RepositoryInjector.getRepository<IPriceOfItemRepository>(call)
        val itemId = call.parameters["itemId"]?.toLong()!!
        call.respond(
            repository.getSingleByIdOrderByTime(itemId)
                .adapt<PriceOfItem, GetPriceOfItemDTO>()
        )
    }

    get("/items/{itemId}/prices/{id}") {
        val repository = RepositoryInjector.getRepository<IPriceOfItemRepository>(call)
        val id = call.parameters["id"]?.toLong()
        val priceOfItemDTO = repository.getById(id!!).adapt<PriceOfItem, GetPriceOfItemDTO>()
        call.respond(priceOfItemDTO)
    }

    post("/items/{itemId}/prices") {
        val priceOfItem = call.receiveAndAdapt<PostPriceOfItemDTO, PriceOfItem>()
        priceOfItem.itemId = call.parameters["itemId"]?.toLong()!!
        val repository = RepositoryInjector.getRepository<IPriceOfItemRepository>(call)
        val id: Long = repository.add(priceOfItem)
        call.respond(id)
    }
}