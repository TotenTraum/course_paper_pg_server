package com.traum.routes

import com.traum.RepositoryInjector
import com.traum.adapt
import com.traum.dtos.booking.GetBookingDTO
import com.traum.dtos.booking.PostBookingDTO
import com.traum.models.Booking
import com.traum.receiveAndAdapt
import com.traum.repositories.interfaces.IBookingRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.bookings() {
    get("/bookings") {
        val repository = RepositoryInjector.getRepository<IBookingRepository>(call)
        call.respond(repository.getAll().map {
            it.adapt<Booking, GetBookingDTO>()
        })
    }

    delete("/bookings/{id}") {
        val repository = RepositoryInjector.getRepository<IBookingRepository>(call)
        val id = call.parameters["id"]?.toLong()
        repository.delete(id!!)
        call.respond(HttpStatusCode.OK)
    }

    get("/bookings/{id}") {
        val repository = RepositoryInjector.getRepository<IBookingRepository>(call)
        val id = call.parameters["id"]?.toLong()
        val bookingDTO = repository.getById(id!!).adapt<Booking, GetBookingDTO>()
        call.respond(bookingDTO)
    }

    post("/bookings") {
        val booking = call.receiveAndAdapt<PostBookingDTO, Booking>()
        val repository = RepositoryInjector.getRepository<IBookingRepository>(call)
        val id: Long = repository.add(booking)
        call.respond(id)
    }
}