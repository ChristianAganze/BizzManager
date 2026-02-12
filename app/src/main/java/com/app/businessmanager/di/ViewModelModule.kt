package com.app.businessmanager.di

import com.app.businessmanager.ui.viewmodel.*
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AuthViewModel(get()) }
    viewModel { DashboardViewModel(get(), get()) }
    viewModel { WorkerViewModel(get(), get(), get(), get(), get()) }
    viewModel { ClientViewModel(get(), get(), get()) }
    viewModel { WorkerManagementViewModel(get(), get(), get()) }
}
