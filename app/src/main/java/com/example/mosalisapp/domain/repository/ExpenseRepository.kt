package com.example.mosalisapp.domain.repository

import com.example.mosalisapp.domain.model.Expense

interface ExpenseRepository {
    suspend fun addExpense(expense: Expense): Result<Unit>
    suspend fun getExpenses(businessId: String): List<Expense>
    suspend fun deleteExpense(id: String): Result<Unit>
}
