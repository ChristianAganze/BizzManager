package com.example.mosalisapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mosalisapp.domain.model.User
import com.example.mosalisapp.domain.repository.AuthRepository
import com.example.mosalisapp.domain.repository.BusinessRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class WorkerManagementViewModel(
    private val businessRepository: BusinessRepository,
    private val authRepository: AuthRepository,
    private val authViewModel: AuthViewModel
) : ViewModel() {

    private val businessId = authViewModel.currentUser.map { it?.businessId ?: "" }

    val workers: StateFlow<List<User>> = businessId.flatMapLatest { id ->
        if (id.isEmpty()) flowOf(emptyList()) else businessRepository.getWorkers(id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addWorker(name: String, email: String, password: String) {
        viewModelScope.launch {
            val bId = authViewModel.currentUser.value?.businessId ?: return@launch
            // We use AuthRepository to register a worker
            // In a real app, this might be a cloud function to avoid logging out the owner
            // For now, we simulate or use a specific method if available
            authRepository.registerWorker(name, email, password, bId)
        }
    }
}
