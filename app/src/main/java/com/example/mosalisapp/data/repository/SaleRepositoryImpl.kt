package com.example.mosalisapp.data.repository

import com.example.mosalisapp.data.mapper.toMap
import com.example.mosalisapp.data.mapper.toSale
import com.example.mosalisapp.domain.model.Sale
import com.example.mosalisapp.domain.repository.SaleRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlin.collections.emptyList

class SaleRepositoryImpl(
    private val firestore: FirebaseFirestore
) : SaleRepository {

    override fun getSales(businessId: String): Flow<List<Sale>> = callbackFlow {
        val subscription = firestore.collection("sales")
            .whereEqualTo("businessId", businessId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val sales = snapshot?.documents?.mapNotNull { it.data?.toSale(it.id) } ?: emptyList()
                trySend(sales)
            }
        awaitClose { subscription.remove() }
    }

    override suspend fun createSale(sale: Sale): Result<String> {
        return try {
            val docRef = firestore.collection("sales").document()
            docRef.set(sale.toMap()).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
