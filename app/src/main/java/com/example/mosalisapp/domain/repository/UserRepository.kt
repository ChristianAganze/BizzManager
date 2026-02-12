package com.example.mosalisapp.domain.repository

import com.example.mosalisapp.domain.model.User

interface UserRepository {
    fun saveUser(user: User)
    suspend fun getUser(uid: String): User?
    suspend fun getWorkersByBusiness(businessId: String): List<User>
    suspend fun createWorker(worker: User): Result<Unit>
}
