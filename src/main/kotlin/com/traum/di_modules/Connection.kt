package com.traum.di_modules

import com.traum.factories.ConnectionFactoryImpl
import com.traum.factories.IConnectionFactory
import com.traum.scoped
import io.ktor.server.application.*
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Модуль подключения к базе данных
 */
val ConnectionModule = scoped<Application, Module>{
    module {
        single<IConnectionFactory> {
            val url = environment.config.property("postgres.url").getString()
            ConnectionFactoryImpl(url)
        }
    }
}