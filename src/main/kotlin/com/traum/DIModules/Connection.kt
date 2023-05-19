package com.traum.DIModules

import com.traum.scoped
import com.traum.plugins.connectToPostgres
import io.ktor.server.application.*
import org.koin.core.module.Module
import org.koin.dsl.module
import java.sql.Connection

/**
 * Модуль подключения к базе данных
 */
val ConnectionModule = scoped<Application, Module>{
    module {
        single<Connection> {
            this@scoped.connectToPostgres()
        }
    }
}