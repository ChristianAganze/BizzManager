package com.example.mosalisapp.domain.repository

import com.example.mosalisapp.domain.model.Sale
import kotlinx.coroutines.flow.Flow

interface SaleRepository {
    fun getSales(businessId: String): Flow<List<Sale>>
    suspend fun createSale(sale: Sale): Result<String>
    suspend fun updateSale(sale: Sale): Result<Unit>
    suspend fun deleteSale(id: String): Result<Unit>
}
