package com.example.mosalisapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mosalisapp.domain.model.Client
import com.example.mosalisapp.domain.usecase.CreateClientUseCase
import com.example.mosalisapp.domain.usecase.GetClientsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ClientViewModel(
    private val getClientsUseCase: GetClientsUseCase,
    private val createClientUseCase: CreateClientUseCase,
    private val authViewModel: AuthViewModel
) : ViewModel() {

    private val businessId = authViewModel.currentUser.map { it?.businessId ?: "" }

    @OptIn(ExperimentalCoroutinesApi::class)
    val clients: StateFlow<List<Client>> = businessId.flatMapLatest { id ->
        if (id.isEmpty()) flowOf(emptyList()) else getClientsUseCase(id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addClient(name: String, phone: String, email: String) {
        viewModelScope.launch {
            val bId = authViewModel.currentUser.value?.businessId ?: return@launch
            val client = Client(
                businessId = bId,
                name = name,
                phone = phone,
                email = email
            )
            createClientUseCase(client)
        }
    }
}
