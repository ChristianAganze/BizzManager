package com.example.mosalisapp.data.repository

import com.example.mosalisapp.domain.model.Expense
import com.example.mosalisapp.domain.repository.ExpenseRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ExpenseRepositoryImpl(
    private val firestore: FirebaseFirestore
) : ExpenseRepository {

    private val expenseCollection = firestore.collection("expenses")

    override suspend fun addExpense(expense: Expense): Result<Unit> {
        return try {
            val docRef = expenseCollection.document()
            docRef.set(expense.copy(id = docRef.id)).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getExpenses(businessId: String): List<Expense> {
        return try {
            expenseCollection
                .whereEqualTo("businessId", businessId)
                .get()
                .await()
                .toObjects(Expense::class.java)
                .sortedByDescending { it.timestamp }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun deleteExpense(id: String): Result<Unit> {
        return try {
            expenseCollection.document(id).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}