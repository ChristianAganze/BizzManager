package com.app.businessmanager.di

import com.app.businessmanager.domain.usecase.*
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetDashboardStatsUseCase(get(), get(), get(), get()) }
    factory { CreateSaleUseCase(get()) }
    factory { CreateDebtUseCase(get()) }
    factory { GetClientsUseCase(get()) }
    factory { CreateClientUseCase(get()) }
    factory { GetProductsUseCase(get()) }
    factory { GetExpensesUseCase(get()) }
    factory { GetAgendaEventsUseCase(get()) }
}
