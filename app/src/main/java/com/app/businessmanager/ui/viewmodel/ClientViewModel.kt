package com.app.businessmanager.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.businessmanager.domain.model.Client
import com.app.businessmanager.domain.usecase.CreateClientUseCase
import com.app.businessmanager.domain.usecase.GetClientsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ClientViewModel(
    private val getClientsUseCase: GetClientsUseCase,
    private val createClientUseCase: CreateClientUseCase,
    private val authViewModel: AuthViewModel
) : ViewModel() {

    private val businessId = authViewModel.currentUser.map { it?.businessId ?: "" }

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
