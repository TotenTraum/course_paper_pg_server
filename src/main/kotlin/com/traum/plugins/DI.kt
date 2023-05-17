package com.traum.plugins

import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.registrateDI() {
    install(Koin) {
        slf4jLogger()

    }
}