package com.example.mosalisapp.domain.usecase

import com.example.mosalisapp.domain.model.Product
import com.example.mosalisapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow


class GetProductsUseCase(private val repository: ProductRepository) {
    operator fun invoke(businessId: String): Flow<List<Product>> {
        return repository.getProducts(businessId)
    }
}
