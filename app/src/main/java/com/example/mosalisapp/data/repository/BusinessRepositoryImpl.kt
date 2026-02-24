package com.example.mosalisapp.data.repository

import com.example.mosalisapp.data.mapper.toBusiness
import com.example.mosalisapp.data.mapper.toMap
import com.example.mosalisapp.data.mapper.toUser
import com.example.mosalisapp.domain.model.Business
import com.example.mosalisapp.domain.model.User
import com.example.mosalisapp.domain.repository.BusinessRepository
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

    override fun getWorkers(businessId: String): Flow<List<User>> = callbackFlow {
        val subscription = firestore.collection("businesses")
            .document(businessId)
            .collection("workers")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val workers: List<User> = snapshot?.documents?.mapNotNull { doc ->
                    doc.data?.toUser(doc.id)
                } ?: emptyList()

                trySend(workers)
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
