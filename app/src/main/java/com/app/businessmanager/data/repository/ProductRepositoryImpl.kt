package com.app.businessmanager.data.repository

import com.app.businessmanager.data.mapper.toMap
import com.app.businessmanager.data.mapper.toProduct
import com.app.businessmanager.domain.model.Product
import com.app.businessmanager.domain.repository.ProductRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ProductRepositoryImpl(
    private val firestore: FirebaseFirestore
) : ProductRepository {

    override fun getProducts(businessId: String): Flow<List<Product>> = callbackFlow {
        val subscription = firestore.collection("products")
            .whereEqualTo("businessId", businessId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val products = snapshot?.documents?.mapNotNull { it.data?.toProduct(it.id) } ?: emptyList()
                trySend(products)
            }
        awaitClose { subscription.remove() }
    }

    override suspend fun addProduct(product: Product): Result<String> {
        return try {
            val docRef = firestore.collection("products").document()
            docRef.set(product.toMap()).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProduct(product: Product): Result<Unit> {
        return try {
            firestore.collection("products").document(product.id)
                .update(product.toMap()).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteProduct(id: String): Result<Unit> {
        return try {
            firestore.collection("products").document(id).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
