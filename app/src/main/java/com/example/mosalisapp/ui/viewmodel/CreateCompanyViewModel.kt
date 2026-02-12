package com.example.mosalisapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mosalisapp.domain.model.Business
import com.example.mosalisapp.domain.repository.BusinessRepository
import com.example.mosalisapp.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore // Import manquant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateCompanyViewModel(
    private val businessRepository: BusinessRepository,
    private val userRepository: UserRepository,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _creationState = MutableStateFlow<CreationState>(CreationState.Idle)
    val creationState = _creationState.asStateFlow()

    fun createCompany(name: String, phone: String, category: String, address: String) {
        viewModelScope.launch {
            _creationState.value = CreationState.Loading
            val uid = auth.currentUser?.uid ?: return@launch

            // Génération d'un ID unique via Firestore
            val businessId = firestore.collection("businesses").document().id

            val newBusiness = Business(
                businessId = businessId,
                name = name,
                type = category,
                address = address,
                phone = phone,
                ownerId = uid
            )

            // 1. Créer le business dans la collection "businesses"
            businessRepository.createBusiness(newBusiness).onSuccess {
                // 2. Mettre à jour le champ businessId de l'utilisateur (OWNER)
                val user = userRepository.getUser(uid)
                if (user != null) {
                    val updatedUser = user.copy(businessId = businessId)
                    userRepository.saveUser(updatedUser)
                    _creationState.value = CreationState.Success
                } else {
                    _creationState.value = CreationState.Error("Utilisateur introuvable")
                }
            }.onFailure {
                _creationState.value = CreationState.Error(it.message ?: "Erreur de création")
            }
        }
    }

    fun resetCreationState() { _creationState.value = CreationState.Idle }
}



sealed class CreationState {
    object Idle : CreationState()
    object Loading : CreationState()
    object Success : CreationState()
    data class Error(val message: String) : CreationState()
}

