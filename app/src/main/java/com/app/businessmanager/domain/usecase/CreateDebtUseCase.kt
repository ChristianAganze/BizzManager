package com.app.businessmanager.domain.usecase

import com.app.businessmanager.domain.model.Debt
import com.app.businessmanager.domain.repository.DebtRepository

class CreateDebtUseCase(private val repository: DebtRepository) {
    suspend operator fun invoke(debt: Debt): Result<String> {
        return repository.createDebt(debt)
    }
}
