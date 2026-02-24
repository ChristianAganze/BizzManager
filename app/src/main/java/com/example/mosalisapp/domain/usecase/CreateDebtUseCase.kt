package com.example.mosalisapp.domain.usecase

import com.example.mosalisapp.domain.model.Debt
import com.example.mosalisapp.domain.repository.DebtRepository

class CreateDebtUseCase(private val repository: DebtRepository) {
    suspend operator fun invoke(debt: Debt): Result<String> {
        return repository.createDebt(debt)
    }
}
