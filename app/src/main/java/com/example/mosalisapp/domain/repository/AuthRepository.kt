package com.example.mosalisapp.domain.repository

import com.example.mosalisapp.domain.model.User
interface AuthRepository {
    suspend fun login(email: String, pass: String): Result<User>
    suspend fun signUpOwner(email: String, pass: String, userName: String): Result<String>

    // Fonction pour l'Owner afin de créer un employé
    suspend fun createWorker(email: String, pass: String, name: String, businessId: String): Result<Unit>

    // Pour la gestion de la liste des employés
    suspend fun getWorkersByBusiness(businessId: String): List<User>

    fun getCurrentUser(): User?
    fun logout(): Result<Unit>
}