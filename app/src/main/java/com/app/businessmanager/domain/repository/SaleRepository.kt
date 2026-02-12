package com.app.businessmanager.domain.repository

import com.app.businessmanager.domain.model.Sale
import kotlinx.coroutines.flow.Flow

interface SaleRepository {
    fun getSales(businessId: String): Flow<List<Sale>>
    suspend fun createSale(sale: Sale): Result<String>
}
