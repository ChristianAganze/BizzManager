package com.example.mosalisapp.di

import org.koin.core.module.dsl.viewModel
import com.example.mosalisapp.data.repository.AuthRepositoryImpl
import com.example.mosalisapp.data.repository.BusinessRepositoryImpl
import com.example.mosalisapp.data.repository.DebtRepositoryImpl
import com.example.mosalisapp.data.repository.ExpenseRepositoryImpl
import com.example.mosalisapp.data.repository.ProductRepositoryImpl
import com.example.mosalisapp.data.repository.SaleRepositoryImpl
import com.example.mosalisapp.data.repository.UserRepositoryImpl
import com.example.mosalisapp.domain.repository.AuthRepository
import com.example.mosalisapp.domain.repository.BusinessRepository
import com.example.mosalisapp.domain.repository.DebtRepository
import com.example.mosalisapp.domain.repository.ExpenseRepository
import com.example.mosalisapp.domain.repository.ProductRepository
import com.example.mosalisapp.domain.repository.SaleRepository
import com.example.mosalisapp.domain.repository.UserRepository
import com.example.mosalisapp.ui.LoginViewModel
import com.example.mosalisapp.ui.screens.auth.AuthViewModel
import com.example.mosalisapp.ui.screens.auth.UsersViewModel
import com.example.mosalisapp.ui.viewmodel.CreateCompanyViewModel
import com.example.mosalisapp.ui.viewmodel.DebtViewModel
import com.example.mosalisapp.ui.viewmodel.ExpenseViewModel
import com.example.mosalisapp.ui.viewmodel.ProductViewModel
import com.example.mosalisapp.ui.viewmodel.SaleViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.dsl.module

val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }


    single<AuthRepository> { AuthRepositoryImpl(get(),get()) }
    single<ProductRepository> { ProductRepositoryImpl(get()) }
    single<BusinessRepository> { BusinessRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<SaleRepository> { SaleRepositoryImpl(get()) }
    single<ExpenseRepository> { ExpenseRepositoryImpl(get()) }
    single<DebtRepository> { DebtRepositoryImpl(get()) }




    viewModel { AuthViewModel(get(),get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { CreateCompanyViewModel(get(),get(),get(),get()) }
    viewModel { UsersViewModel(get(), get()) }
    viewModel { ProductViewModel(get(), get()) }
    viewModel { SaleViewModel(get(), get(),get()) }
    viewModel { ExpenseViewModel(get(), get()) }
    viewModel { DebtViewModel(get(), get()) }




}