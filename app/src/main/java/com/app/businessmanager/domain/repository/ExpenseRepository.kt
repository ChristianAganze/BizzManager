package com.app.businessmanager.domain.repository

import com.app.businessmanager.domain.model.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun getExpenses(businessId: String): Flow<List<Expense>>
    suspend fun createExpense(expense: Expense): Result<String>
}
