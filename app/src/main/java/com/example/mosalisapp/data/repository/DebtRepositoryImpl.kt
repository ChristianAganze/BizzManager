package com.example.mosalisapp.data.repository

import com.example.mosalisapp.domain.model.Debt
import com.example.mosalisapp.domain.repository.DebtRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class DebtRepositoryImpl(private val firestore: FirebaseFirestore) : DebtRepository {
    private val debtCollection = firestore.collection("debts")

    override suspend fun addDebt(debt: Debt): Result<Unit> {
        return try {
            val doc = debtCollection.document()
            doc.set(debt.copy(id = doc.id)).await()
            Result.success(Unit)
        } catch (e: Exception) { Result.failure(e) }
    }

    override suspend fun getDebts(businessId: String): List<Debt> {
        return try {
            debtCollection.whereEqualTo("businessId", businessId)
                .get().await().toObjects(Debt::class.java)
                .sortedByDescending { it.timestamp }
        } catch (e: Exception) { emptyList() }
    }

    override suspend fun updateDebtStatus(debtId: String, isPaid: Boolean): Result<Unit> {
        return try {
            debtCollection.document(debtId).update("paid", isPaid).await()
            Result.success(Unit)
        } catch (e: Exception) { Result.failure(e) }
    }

    override suspend fun deleteDebt(debtId: String): Result<Unit> {
        return try {
            debtCollection.document(debtId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) { Result.failure(e) }
    }
}