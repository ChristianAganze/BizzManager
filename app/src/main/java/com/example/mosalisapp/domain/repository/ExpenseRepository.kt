package com.example.mosalisapp.domain.repository

import com.example.mosalisapp.domain.model.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun getExpenses(businessId: String): Flow<List<Expense>>
    suspend fun createExpense(expense: Expense): Result<String>
}
