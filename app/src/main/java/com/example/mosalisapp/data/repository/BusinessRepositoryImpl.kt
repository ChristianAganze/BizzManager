package com.example.mosalisapp.data.repository

import com.example.mosalisapp.domain.model.Business
import com.example.mosalisapp.domain.repository.BusinessRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class BusinessRepositoryImpl(private val firestore: FirebaseFirestore) : BusinessRepository {
    override suspend fun createBusiness(business: Business): Result<Unit> = try {
        firestore.collection("businesses")
            .document(business.businessId)
            .set(business)
            .await()
        Result.success(Unit)
    } catch (e: Exception) { Result.failure(e) }

    override suspend fun getBusiness(businessId: String): Business? = try {
        firestore.collection("businesses").document(businessId).get().await().toObject(Business::class.java)
    } catch (e: Exception) { null }
}
