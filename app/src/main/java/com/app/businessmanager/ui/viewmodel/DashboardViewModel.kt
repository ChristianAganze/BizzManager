package com.app.businessmanager.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.businessmanager.domain.model.DashboardStats
import com.app.businessmanager.domain.usecase.GetDashboardStatsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn

class DashboardViewModel(
    private val getDashboardStatsUseCase: GetDashboardStatsUseCase,
    private val authViewModel: AuthViewModel
) : ViewModel() {

    private val businessId = authViewModel.currentUser.flatMapLatest { user ->
        flowOf(user?.businessId ?: "")
    }

    val dashboardState: StateFlow<DashboardState> = businessId.flatMapLatest { id ->
        if (id.isEmpty()) {
            flowOf(DashboardState.Error("No business ID found"))
        } else {
            getDashboardStatsUseCase(id).flatMapLatest { stats ->
                flowOf(DashboardState.Success(stats))
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DashboardState.Loading)
}

sealed class DashboardState {
    object Loading : DashboardState()
    data class Success(val stats: DashboardStats) : DashboardState()
    data class Error(val message: String) : DashboardState()
}
