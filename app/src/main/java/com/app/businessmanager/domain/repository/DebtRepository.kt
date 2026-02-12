package com.app.businessmanager.domain.repository

import com.app.businessmanager.domain.model.Debt
import kotlinx.coroutines.flow.Flow

interface DebtRepository {
    fun getDebts(businessId: String): Flow<List<Debt>>
    suspend fun createDebt(debt: Debt): Result<String>
    suspend fun updateDebtStatus(debtId: String, isPaid: Boolean): Result<Unit>
}
