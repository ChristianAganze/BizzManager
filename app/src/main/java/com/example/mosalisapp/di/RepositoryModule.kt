package com.example.mosalisapp.di

import com.example.mosalisapp.data.repository.AgendaRepositoryImpl
import com.example.mosalisapp.data.repository.AuthRepositoryImpl
import com.example.mosalisapp.data.repository.BusinessRepositoryImpl
import com.example.mosalisapp.data.repository.ClientRepositoryImpl
import com.example.mosalisapp.data.repository.DebtRepositoryImpl
import com.example.mosalisapp.data.repository.ExpenseRepositoryImpl
import com.example.mosalisapp.data.repository.ProductRepositoryImpl
import com.example.mosalisapp.data.repository.SaleRepositoryImpl
import com.example.mosalisapp.domain.repository.AgendaRepository
import com.example.mosalisapp.domain.repository.AuthRepository
import com.example.mosalisapp.domain.repository.BusinessRepository
import com.example.mosalisapp.domain.repository.ClientRepository
import com.example.mosalisapp.domain.repository.DebtRepository
import com.example.mosalisapp.domain.repository.ExpenseRepository
import com.example.mosalisapp.domain.repository.ProductRepository
import com.example.mosalisapp.domain.repository.SaleRepository
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
