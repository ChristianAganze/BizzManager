package com.example.mosalisapp.domain.usecase

import com.example.mosalisapp.domain.model.Expense
import com.example.mosalisapp.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow

class GetExpensesUseCase(private val repository: ExpenseRepository) {
    operator fun invoke(businessId: String): Flow<List<Expense>> {
        return repository.getExpenses(businessId)
    }
}
