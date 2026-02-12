package com.app.businessmanager.domain.usecase

import com.app.businessmanager.domain.model.Sale
import com.app.businessmanager.domain.repository.SaleRepository

class CreateSaleUseCase(private val repository: SaleRepository) {
    suspend operator fun invoke(sale: Sale): Result<String> {
        return repository.createSale(sale)
    }
}
