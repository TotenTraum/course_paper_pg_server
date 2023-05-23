package com.traum.routes

import com.traum.RepositoryInjector
import com.traum.adapt
import com.traum.dtos.measures.GetMeasurementDTO
import com.traum.dtos.measures.PatchMeasurementDTO
import com.traum.dtos.measures.PostMeasurementDTO
import com.traum.models.Measurement
import com.traum.receiveAndAdapt
import com.traum.repositories.interfaces.IMeasurementRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.measurements() {
    get("/measurements") {
        val repository = RepositoryInjector.getRepository<IMeasurementRepository>(call)
        call.respond(repository.getAll().map {
            it.adapt<Measurement, GetMeasurementDTO>()
        })
    }

    delete("/measurements/{id}") {
        val repository = RepositoryInjector.getRepository<IMeasurementRepository>(call)
        val id = call.parameters["id"]?.toLong()
        repository.delete(id!!)
        call.respond(HttpStatusCode.OK)
    }

    get("/measurements/{id}") {
        val repository = RepositoryInjector.getRepository<IMeasurementRepository>(call)
        val id = call.parameters["id"]?.toLong()
        val measurementDTO = repository.getById(id!!).adapt<Measurement, GetMeasurementDTO>()
        call.respond(measurementDTO)
    }

    patch("/measurements/{id}") {
        val repository = RepositoryInjector.getRepository<IMeasurementRepository>(call)
        val id = call.parameters["id"]?.toLong()
        val measurement = repository.getById(id!!)
        call.receive<PatchMeasurementDTO>().adapt(measurement)
        repository.update(measurement)
        call.respond(HttpStatusCode.OK)
    }

    post("/measurements") {
        val measurement = call.receiveAndAdapt<PostMeasurementDTO, Measurement>()
        val repository = RepositoryInjector.getRepository<IMeasurementRepository>(call)
        val id: Long = repository.add(measurement)
        call.respond(id)
    }
}