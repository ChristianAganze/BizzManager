package com.example.mosalisapp.domain.repository

import com.example.mosalisapp.domain.model.Business
import com.example.mosalisapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface BusinessRepository {
    fun getBusiness(id: String): Flow<Business?>
    suspend fun createBusiness(business: Business): Result<String>
    suspend fun updateBusiness(business: Business): Result<Unit>
    fun getWorkers(businessId: String): Flow<List<User>>
}
