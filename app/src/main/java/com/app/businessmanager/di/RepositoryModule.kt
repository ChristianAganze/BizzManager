package com.app.businessmanager.di

import com.app.businessmanager.data.repository.*
import com.app.businessmanager.domain.repository.*
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<BusinessRepository> { BusinessRepositoryImpl(get()) }
    single<ProductRepository> { ProductRepositoryImpl(get()) }
    single<SaleRepository> { SaleRepositoryImpl(get()) }
    single<ClientRepository> { ClientRepositoryImpl(get()) }
    single<DebtRepository> { DebtRepositoryImpl(get()) }
    single<ExpenseRepository> { ExpenseRepositoryImpl(get()) }
    single<AgendaRepository> { AgendaRepositoryImpl(get()) }
}
