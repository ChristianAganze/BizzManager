package com.app.businessmanager.domain.repository

import com.app.businessmanager.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(businessId: String): Flow<List<Product>>
    suspend fun addProduct(product: Product): Result<String>
    suspend fun updateProduct(product: Product): Result<Unit>
    suspend fun deleteProduct(id: String): Result<Unit>
}
