package com.app.businessmanager.data.repository

import com.app.businessmanager.data.mapper.toBusiness
import com.app.businessmanager.data.mapper.toMap
import com.app.businessmanager.domain.model.Business
import com.app.businessmanager.domain.repository.BusinessRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class BusinessRepositoryImpl(
    private val firestore: FirebaseFirestore
) : BusinessRepository {

    override fun getBusiness(id: String): Flow<Business?> = callbackFlow {
        val subscription = firestore.collection("businesses").document(id)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val business = snapshot?.data?.toBusiness(id)
                trySend(business)
            }
        awaitClose { subscription.remove() }
    }

    override suspend fun createBusiness(business: Business): Result<String> {
        return try {
            val docRef = firestore.collection("businesses").document()
            val newBusiness = business.copy(id = docRef.id)
            docRef.set(newBusiness.toMap()).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateBusiness(business: Business): Result<Unit> {
        return try {
            firestore.collection("businesses").document(business.id)
                .update(business.toMap()).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
