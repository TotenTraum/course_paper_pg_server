package com.traum.plugins

import com.traum.di_modules.ConnectionModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureDI() {
    install(Koin) {
        slf4jLogger()
        modules(ConnectionModule(this@configureDI))
    }
}