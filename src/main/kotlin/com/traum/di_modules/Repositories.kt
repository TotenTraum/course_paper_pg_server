package com.traum.di_modules

import com.traum.repositories.implementations.*
import com.traum.repositories.interfaces.*
import com.traum.scoped
import io.ktor.server.application.*
import org.koin.core.module.Module
import org.koin.dsl.module

val RepositoriesModule = scoped<Application, Module> {
    module {
        factory<IOrderRepository> { OrderRepositoryImpl(get()) }
        factory<IBookingRepository> { BookingRepositoryImpl(get()) }
        factory<ICategoryRepository> { CategoryRepositoryImpl(get()) }
        factory<IElementOfOrderRepository> { ElementOfOrderRepositoryImpl(get()) }
        factory<IEmployeeRepository> { EmployeeRepositoryImpl(get()) }
        factory<IItemRepository> { ItemRepositoryImpl(get()) }
        factory<ILogRepository> { LogRepositoryImpl(get()) }
        factory<IMeasurementRepository> { MeasurementRepositoryImpl(get()) }
        factory<IMeasureOfItemRepository> { MeasureOfItemRepositoryImpl(get()) }
        factory<IPriceOfItemRepository> { PriceOfItemRepositoryImpl(get()) }
        factory<ITableRepository> { TableRepositoryImpl(get()) }
    }
}