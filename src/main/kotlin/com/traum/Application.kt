package com.traum

import io.ktor.server.application.*
import com.traum.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureDI()
    configureSecurity()
    configureHTTP()
    configureSerialization()
    configureDatabases()
    configureAdministration()
    configureRouting()
}
