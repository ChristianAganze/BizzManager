package com.example.mosalisapp.data.repository

import com.example.mosalisapp.data.mapper.toDebt
import com.example.mosalisapp.data.mapper.toMap
import com.example.mosalisapp.domain.model.Debt
import com.example.mosalisapp.domain.repository.DebtRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class DebtRepositoryImpl(
    private val firestore: FirebaseFirestore
) : DebtRepository {

    override fun getDebts(businessId: String): Flow<List<Debt>> = callbackFlow {
        val subscription = firestore.collection("debts")
            .whereEqualTo("businessId", businessId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val debts = snapshot?.documents?.mapNotNull { it.data?.toDebt(it.id) } ?: emptyList()
                trySend(debts)
            }
        awaitClose { subscription.remove() }
    }

    override suspend fun createDebt(debt: Debt): Result<String> {
        return try {
            val docRef = firestore.collection("debts").document()
            docRef.set(debt.toMap()).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateDebtStatus(debtId: String, isPaid: Boolean): Result<Unit> {
        return try {
            firestore.collection("debts").document(debtId)
                .update("isPaid", isPaid).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
