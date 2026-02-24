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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get<FirebaseAuth>(), get<FirebaseFirestore>()) }
    single<BusinessRepository> { BusinessRepositoryImpl(get<FirebaseFirestore>()) }
    single<ProductRepository> { ProductRepositoryImpl(get<FirebaseFirestore>()) }
    single<SaleRepository> { SaleRepositoryImpl(get<FirebaseFirestore>()) }
    single<ClientRepository> { ClientRepositoryImpl(get<FirebaseFirestore>()) }
    single<DebtRepository> { DebtRepositoryImpl(get<FirebaseFirestore>()) }
    single<ExpenseRepository> { ExpenseRepositoryImpl(get<FirebaseFirestore>()) }
    single<AgendaRepository> { AgendaRepositoryImpl(get<FirebaseFirestore>()) }
}
