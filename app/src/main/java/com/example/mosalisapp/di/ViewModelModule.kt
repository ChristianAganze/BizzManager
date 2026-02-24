package com.example.mosalisapp.di

import com.example.mosalisapp.ui.viewmodel.AuthViewModel
import com.example.mosalisapp.ui.viewmodel.ClientViewModel
import com.example.mosalisapp.ui.viewmodel.DashboardViewModel
import com.example.mosalisapp.ui.viewmodel.WorkerManagementViewModel
import com.example.mosalisapp.ui.viewmodel.WorkerViewModel
import com.example.mosalisapp.ui.viewmodel.SaleViewModel
import com.example.mosalisapp.ui.viewmodel.BusinessViewModel
import com.example.mosalisapp.ui.viewmodel.ThemeViewModel
import com.example.mosalisapp.domain.repository.*
import com.example.mosalisapp.domain.usecase.*
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AuthViewModel(get<AuthRepository>()) }
    viewModel { DashboardViewModel(get<GetDashboardStatsUseCase>(), get<AuthViewModel>()) }
    viewModel { 
        WorkerViewModel(
            createSaleUseCase = get<CreateSaleUseCase>(),
            createDebtUseCase = get<CreateDebtUseCase>(),
            createExpenseUseCase = get<CreateExpenseUseCase>(),
            createClientUseCase = get<CreateClientUseCase>(),
            getProductsUseCase = get<GetProductsUseCase>(),
            authViewModel = get<AuthViewModel>()
        )
    }
    viewModel { ClientViewModel(get<GetClientsUseCase>(), get<CreateClientUseCase>(), get<AuthViewModel>()) }
    viewModel { WorkerManagementViewModel(get<BusinessRepository>(), get<AuthRepository>(), get<AuthViewModel>()) }
    viewModel { SaleViewModel(get<SaleRepository>(), get<AuthViewModel>()) }
    viewModel { BusinessViewModel(get<CreateBusinessUseCase>(), get<AuthViewModel>()) }
    viewModel { ThemeViewModel() }
}
