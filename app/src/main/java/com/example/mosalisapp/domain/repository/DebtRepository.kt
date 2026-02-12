package com.example.mosalisapp.domain.repository

import com.example.mosalisapp.domain.model.Debt


interface DebtRepository {

    suspend fun addDebt(debt: Debt): Result<Unit>


    suspend fun getDebts(businessId: String): List<Debt>

    suspend fun updateDebtStatus(debtId: String, isPaid: Boolean): Result<Unit>


    suspend fun deleteDebt(debtId: String): Result<Unit>
}