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
    }
    .catch { e -> 
        _error.value = "Erreur lors du chargement des employés: ${e.message}"
        emit(emptyList())
    }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _isAddingWorker = MutableStateFlow(false)
    val isAddingWorker = _isAddingWorker.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun addWorker(name: String, email: String, password: String, onSuccess: () -> Unit) {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            _error.value = "Tous les champs sont obligatoires"
            return
        }
        
        viewModelScope.launch {
            _isAddingWorker.value = true
            _error.value = null

            // Attendre que l'utilisateur soit chargé pour avoir le businessId
            val currentUser = authViewModel.currentUser.filterNotNull().first()
            val bId = currentUser.businessId
            
            if (bId.isEmpty()) {
                _error.value = "ID d'entreprise manquant. Veuillez vous reconnecter."
                _isAddingWorker.value = false
                return@launch
            }
            
            val result = authRepository.registerWorker(name, email, password, bId)
            _isAddingWorker.value = false
            
            if (result.isSuccess) {
                onSuccess()
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Erreur inconnue"
            }
        }
    }
}
