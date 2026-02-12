package com.example.mosalisapp.domain.repository


import com.example.mosalisapp.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(businessId: String): List<Product>
    suspend fun addProduct(product: Product): Result<Unit>
    suspend fun updateProduct(product: Product): Result<Unit>
    suspend fun deleteProduct(productId: String): Result<Unit>
}
