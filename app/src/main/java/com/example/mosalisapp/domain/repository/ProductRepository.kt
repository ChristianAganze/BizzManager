package com.example.mosalisapp.domain.repository

import com.example.mosalisapp.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(businessId: String): Flow<List<Product>>
    suspend fun addProduct(product: Product): Result<String>
    suspend fun updateProduct(product: Product): Result<Unit>
    suspend fun deleteProduct(id: String): Result<Unit>
}
