package com.example.mosalisapp.data.repository

import com.example.mosalisapp.domain.model.Product
import com.example.mosalisapp.domain.repository.ProductRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProductRepositoryImpl(
    private val firestore: FirebaseFirestore
) : ProductRepository {

    private val productsCollection = firestore.collection("products")

    override suspend fun getProducts(businessId: String): List<Product> = try {
        productsCollection
            .whereEqualTo("businessId", businessId)
            .get()
            .await()
            .toObjects(Product::class.java)
    } catch (e: Exception) { emptyList() }

    override suspend fun addProduct(product: Product): Result<Unit> = try {
        val docRef = productsCollection.document()
        val newProduct = product.copy(id = docRef.id)
        docRef.set(newProduct).await()
        Result.success(Unit)
    } catch (e: Exception) { Result.failure(e) }

    override suspend fun updateProduct(product: Product): Result<Unit> = try {
        productsCollection.document(product.id).set(product).await()
        Result.success(Unit)
    } catch (e: Exception) { Result.failure(e) }

    override suspend fun deleteProduct(productId: String): Result<Unit> = try {
        productsCollection.document(productId).delete().await()
        Result.success(Unit)
    } catch (e: Exception) { Result.failure(e) }
}