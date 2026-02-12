package com.app.businessmanager.domain.usecase

import com.app.businessmanager.domain.model.Product
import com.app.businessmanager.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class GetProductsUseCase(private val repository: ProductRepository) {
    operator fun invoke(businessId: String): Flow<List<Product>> {
        return repository.getProducts(businessId)
    }
}
