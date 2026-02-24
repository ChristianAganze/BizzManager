package com.example.mosalisapp.domain.repository

import com.example.mosalisapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getCurrentUser(): Flow<User?>
    suspend fun login(email: String, password: String): Result<User?>
    suspend fun register(name: String, email: String, password: String): Result<User>
    suspend fun registerWorker(name: String, email: String, password: String, businessId: String): Result<User>
    suspend fun updateBusinessId(userId: String, businessId: String): Result<Unit>
    suspend fun logout()
}
