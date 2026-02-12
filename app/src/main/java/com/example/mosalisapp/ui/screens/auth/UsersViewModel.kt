package com.example.mosalisapp.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mosalisapp.domain.model.User
import com.example.mosalisapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class UsersViewModel(
    private val userRepository: UserRepository,
    private val authViewModel: AuthViewModel // Pour récupérer le businessId de l'Owner actuel
) : ViewModel() {

    private val _workers = MutableStateFlow<List<User>>(emptyList())
    val workers = _workers.asStateFlow()

    init {
        loadWorkers()
    }

    fun loadWorkers() {
        viewModelScope.launch {
            val businessId = authViewModel.currentUser.value?.businessId
            if (businessId != null) {
                _workers.value = userRepository.getWorkersByBusiness(businessId)
            }
        }
    }

    fun addWorker(name: String, email: String, pass: String) {
        viewModelScope.launch {
            val owner = authViewModel.currentUser.value
            if (owner?.businessId != null) {
                // On utilise authRepository (ou authViewModel) pour créer le compte réel
                authViewModel.createWorker(
                    email = email,
                    pass = pass,
                    name = name,
                    businessId = owner.businessId
                )
                loadWorkers() // Recharger la liste après création
            }
        }
    }}