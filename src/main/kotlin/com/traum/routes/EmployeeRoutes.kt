package com.traum.routes

import com.traum.RepositoryInjector
import com.traum.adapt
import com.traum.dtos.employee.GetEmployeeDTO
import com.traum.dtos.employee.PatchEmployeeDTO
import com.traum.dtos.employee.PostEmployeeDTO
import com.traum.models.Employee
import com.traum.receiveAndAdapt
import com.traum.repositories.interfaces.IEmployeeRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.employees() {
    get("/employees") {
        val repository = RepositoryInjector.getRepository<IEmployeeRepository>(call)
        call.respond(repository.getAll().map {
            it.adapt<Employee, GetEmployeeDTO>()
        })
    }

    delete("/employees/{id}") {
        val repository = RepositoryInjector.getRepository<IEmployeeRepository>(call)
        val id = call.parameters["id"]?.toLong()
        repository.delete(id!!)
        call.respond(HttpStatusCode.OK)
    }

    get("/employees/{id}") {
        val repository = RepositoryInjector.getRepository<IEmployeeRepository>(call)
        val id = call.parameters["id"]?.toLong()
        call.respond(repository.getById(id!!).adapt<Employee, GetEmployeeDTO>())
    }

    patch("/employees/{id}") {
        val repository = RepositoryInjector.getRepository<IEmployeeRepository>(call)
        val id = call.parameters["id"]?.toLong()
        val employee = repository.getById(id!!)
        call.receive<PatchEmployeeDTO>().adapt(employee)
        repository.update(employee)
        call.respond(HttpStatusCode.OK)
    }

    post("/employees") {
        val employee = call.receiveAndAdapt<PostEmployeeDTO, Employee>()
        val repository = RepositoryInjector.getRepository<IEmployeeRepository>(call)
        val id: Long = repository.add(employee)
        call.respond(id)
    }
}