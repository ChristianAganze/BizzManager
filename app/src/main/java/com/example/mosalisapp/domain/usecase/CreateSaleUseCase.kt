package com.example.mosalisapp.domain.usecase

import com.example.mosalisapp.domain.model.Sale
import com.example.mosalisapp.domain.repository.SaleRepository


class CreateSaleUseCase(private val repository: SaleRepository) {
    suspend operator fun invoke(sale: Sale): Result<String> {
        return repository.createSale(sale)
    }
}
