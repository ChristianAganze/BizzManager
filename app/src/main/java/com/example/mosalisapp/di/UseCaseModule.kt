package com.example.mosalisapp.di

import com.example.mosalisapp.domain.usecase.*
import com.example.mosalisapp.domain.repository.*
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetDashboardStatsUseCase(get<ProductRepository>(),get<SaleRepository>(), get<DebtRepository>(),get<ExpenseRepository>()  ) }
    factory { CreateSaleUseCase(get<SaleRepository>()) }
    factory { CreateDebtUseCase(get<DebtRepository>()) }
    factory { GetClientsUseCase(get<ClientRepository>()) }
    factory { CreateClientUseCase(get<ClientRepository>()) }
    factory { GetProductsUseCase(get<ProductRepository>()) }
    factory { GetExpensesUseCase(get<ExpenseRepository>()) }
    factory { CreateExpenseUseCase(get<ExpenseRepository>()) }
    factory { CreateBusinessUseCase(get<BusinessRepository>(), get<AuthRepository>()) }
    factory { GetAgendaEventsUseCase(get<AgendaRepository>()) }
}
