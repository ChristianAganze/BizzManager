package com.app.businessmanager.domain.repository

import com.app.businessmanager.domain.model.Business
import kotlinx.coroutines.flow.Flow

interface BusinessRepository {
    fun getBusiness(id: String): Flow<Business?>
    suspend fun createBusiness(business: Business): Result<String>
    suspend fun updateBusiness(business: Business): Result<Unit>
}
