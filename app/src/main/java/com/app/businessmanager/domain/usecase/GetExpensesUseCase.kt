package com.app.businessmanager.domain.usecase

import com.app.businessmanager.domain.model.Expense
import com.app.businessmanager.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow

class GetExpensesUseCase(private val repository: ExpenseRepository) {
    operator fun invoke(businessId: String): Flow<List<Expense>> {
        return repository.getExpenses(businessId)
    }
}
