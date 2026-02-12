package com.example.mosalisapp.domain.repository

import com.example.mosalisapp.domain.model.Business
import kotlinx.coroutines.flow.Flow

interface BusinessRepository {
    suspend fun createBusiness(business: Business): Result<Unit>
    suspend fun getBusiness(businessId: String): Business?
}
