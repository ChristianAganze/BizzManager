package com.example.mosalisapp.domain.repository

import com.example.mosalisapp.domain.model.Sale
import kotlinx.coroutines.flow.Flow



interface SaleRepository {
    suspend fun addSale(sale: Sale): Result<Unit>
    suspend fun getSalesByBusiness(businessId: String): List<Sale>
    //suspend fun getSalesBySeller(sellerId: String): List<Sale>
    //modifier aussi la vente
   // fun getSalesByProduct(productId: String): Flow<List<Sale>>
}
