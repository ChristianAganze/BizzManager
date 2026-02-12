package com.app.businessmanager.data.repository

import com.app.businessmanager.data.mapper.toAgendaEvent
import com.app.businessmanager.data.mapper.toMap
import com.app.businessmanager.domain.model.AgendaEvent
import com.app.businessmanager.domain.repository.AgendaRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AgendaRepositoryImpl(
    private val firestore: FirebaseFirestore
) : AgendaRepository {

    override fun getEvents(businessId: String): Flow<List<AgendaEvent>> = callbackFlow {
        val subscription = firestore.collection("agenda")
            .whereEqualTo("businessId", businessId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val events = snapshot?.documents?.mapNotNull { it.data?.toAgendaEvent(it.id) } ?: emptyList()
                trySend(events)
            }
        awaitClose { subscription.remove() }
    }

    override suspend fun addEvent(event: AgendaEvent): Result<String> {
        return try {
            val docRef = firestore.collection("agenda").document()
            docRef.set(event.toMap()).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
