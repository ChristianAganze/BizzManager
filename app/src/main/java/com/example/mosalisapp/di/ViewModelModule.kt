package com.example.mosalisapp.di

import com.example.mosalisapp.ui.viewmodel.AuthViewModel
import com.example.mosalisapp.ui.viewmodel.ClientViewModel
import com.example.mosalisapp.ui.viewmodel.DashboardViewModel
import com.example.mosalisapp.ui.viewmodel.WorkerManagementViewModel
import com.example.mosalisapp.ui.viewmodel.WorkerViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AuthViewModel(get()) }
    viewModel { DashboardViewModel(get(), get()) }
    viewModel { WorkerViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { ClientViewModel(get(), get(), get()) }
    viewModel { WorkerManagementViewModel(get(), get(), get()) }
    viewModel { SaleViewModel(get(), get()) }
}
