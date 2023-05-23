package com.traum.routes

import com.traum.RepositoryInjector
import com.traum.adapt
import com.traum.dtos.category.GetCategoryDTO
import com.traum.dtos.category.PatchCategoryDTO
import com.traum.dtos.category.PostCategoryDTO
import com.traum.models.Category
import com.traum.receiveAndAdapt
import com.traum.repositories.interfaces.ICategoryRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.categories() {
    get("/categories") {
        val repository = RepositoryInjector.getRepository<ICategoryRepository>(call)
        call.respond(repository.getAll().map {
            it.adapt<Category, GetCategoryDTO>()
        })
    }

    delete("/categories/{id}") {
        val repository = RepositoryInjector.getRepository<ICategoryRepository>(call)
        val id = call.parameters["id"]?.toLong()
        repository.delete(id!!)
        call.respond(HttpStatusCode.OK)
    }

    get("/categories/{id}") {
        val repository = RepositoryInjector.getRepository<ICategoryRepository>(call)
        val id = call.parameters["id"]?.toLong()
        val category = repository.getById(id!!).adapt<Category, GetCategoryDTO>()
        call.respond(category)
    }

    patch("/categories/{id}") {
        val repository = RepositoryInjector.getRepository<ICategoryRepository>(call)
        val id = call.parameters["id"]?.toLong()
        val category = repository.getById(id!!)
        call.receive<PatchCategoryDTO>().adapt(category)
        repository.update(category)
        call.respond(HttpStatusCode.OK)
    }

    post("/categories") {
        val category = call.receiveAndAdapt<PostCategoryDTO, Category>()
        val repository = RepositoryInjector.getRepository<ICategoryRepository>(call)
        val id: Long = repository.add(category)
        call.respond(id)
    }
}