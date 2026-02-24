package com.example.mosalisapp.data.repository

import com.example.mosalisapp.data.mapper.toExpense
import com.example.mosalisapp.data.mapper.toMap
import com.example.mosalisapp.domain.model.Expense
import com.example.mosalisapp.domain.repository.ExpenseRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlin.collections.emptyList

class ExpenseRepositoryImpl(
    private val firestore: FirebaseFirestore
) : ExpenseRepository {

    override fun getExpenses(businessId: String): Flow<List<Expense>> = callbackFlow {
        val subscription = firestore.collection("expenses")
            .whereEqualTo("businessId", businessId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val expenses = snapshot?.documents?.mapNotNull { it.data?.toExpense(it.id) } ?: emptyList()
                trySend(expenses)
            }
        awaitClose { subscription.remove() }
    }

    override suspend fun createExpense(expense: Expense): Result<String> {
        return try {
            val docRef = firestore.collection("expenses").document()
            docRef.set(expense.toMap()).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
