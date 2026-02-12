package com.app.businessmanager.domain.repository

import com.app.businessmanager.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getCurrentUser(): Flow<User?>
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun register(name: String, email: String, password: String): Result<User>
    suspend fun registerWorker(name: String, email: String, password: String, businessId: String): Result<User>
    suspend fun logout()
}
