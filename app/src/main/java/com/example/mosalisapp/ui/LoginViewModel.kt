package com.example.mosalisapp.ui


import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _authState = MutableStateFlow(false)
    val authState: StateFlow<Boolean> = _authState

    fun login(email: String, password: String, onError: (String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _authState.value = true
            }
            .addOnFailureListener {
                onError(it.message ?: "Erreur de connexion")
            }
    }

    fun register(email: String, password: String, onError: (String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _authState.value = true
            }
            .addOnFailureListener {
                onError(it.message ?: "Erreur d’inscription")
            }
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}
