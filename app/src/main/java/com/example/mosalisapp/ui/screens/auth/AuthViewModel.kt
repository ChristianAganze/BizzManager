package com.example.mosalisapp.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mosalisapp.domain.model.User
import com.example.mosalisapp.domain.repository.AuthRepository
import com.example.mosalisapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState = _authState.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    init {
        checkCurrentUser()
    }

    private fun checkCurrentUser() {
        viewModelScope.launch {
            val firebaseUser = authRepository.getCurrentUser()
            if (firebaseUser != null) {
                val user = userRepository.getUser(firebaseUser.userId)
                _currentUser.value = user
                _authState.value = AuthState.Authenticated(user)
            } else {
                _authState.value = AuthState.Unauthenticated
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            authRepository.login(email, password)
                .onSuccess { user ->
                    _currentUser.value = user
                    _authState.value = AuthState.Authenticated(user)
                }
                .onFailure { error ->
                    _authState.value = AuthState.Error(error.message ?: "Erreur de connexion")
                }
        }
    }

    fun signUp(email: String, pass: String, userName: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            authRepository.signUpOwner(
                email, pass, userName,

            )
                .onSuccess {
                    // L'inscription a réussi, maintenant connectons l'utilisateur
                    login(email, pass)
                }
                .onFailure { error ->
                    _authState.value = AuthState.Error(error.message ?: "Erreur d'inscription")
                }
        }
    }
    // Dans ton fichier AuthViewModel.kt
    fun createWorker(email: String, pass: String, name: String, businessId: String) {
        viewModelScope.launch {
            // Optionnel : tu peux mettre un état de chargement ici
            authRepository.createWorker(email, pass, name, businessId)
            // Note : Cette fonction dans le Repository va créer l'auth ET le document Firestore
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _currentUser.value = null
            _authState.value = AuthState.Unauthenticated
        }
    }
    fun AuthViewModel.changePassword(newPassword: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            // Logique Firebase Auth : currentUser?.updatePassword(newPassword)
        }
    }

    fun resetAuthState() {
        if (_authState.value is AuthState.Error) {
            _authState.value = AuthState.Unauthenticated
        }
    }
}

sealed class AuthState {
    object Loading : AuthState()
    data class Authenticated(val user: User?) : AuthState()
    object Unauthenticated : AuthState()
    data class Error(val message: String) : AuthState()
}
