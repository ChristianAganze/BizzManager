package com.example.mosalisapp.di

import com.example.mosalisapp.domain.usecase.*
import com.example.mosalisapp.domain.repository.*
import org.koin.dsl.module

val useCaseModule = module {
    factory<GetDashboardStatsUseCase> { GetDashboardStatsUseCase(get(), get(), get(), get()) }
    factory<CreateSaleUseCase> { CreateSaleUseCase(get(), get()) }
    factory<DeleteSaleUseCase> { DeleteSaleUseCase(get(), get()) }
    factory<CreateDebtUseCase> { CreateDebtUseCase(get()) }
    factory<GetClientsUseCase> { GetClientsUseCase(get()) }
    factory<CreateClientUseCase> { CreateClientUseCase(get()) }
    factory<GetProductsUseCase> { GetProductsUseCase(get()) }
    factory<GetExpensesUseCase> { GetExpensesUseCase(get()) }
    factory<CreateExpenseUseCase> { CreateExpenseUseCase(get()) }
    factory<CreateBusinessUseCase> { CreateBusinessUseCase(get(), get()) }
    factory<GetAgendaEventsUseCase> { GetAgendaEventsUseCase(get()) }
}
