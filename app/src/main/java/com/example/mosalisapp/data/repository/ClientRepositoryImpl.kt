package com.example.mosalisapp.data.repository

import com.example.mosalisapp.data.mapper.toClient
import com.example.mosalisapp.data.mapper.toMap
import com.example.mosalisapp.domain.model.Client
import com.example.mosalisapp.domain.repository.ClientRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlin.collections.emptyList

class ClientRepositoryImpl(
    private val firestore: FirebaseFirestore
) : ClientRepository {

    override fun getClients(businessId: String): Flow<List<Client>> = callbackFlow {
        val subscription = firestore.collection("clients")
            .whereEqualTo("businessId", businessId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val clients = snapshot?.documents?.mapNotNull { it.data?.toClient(it.id) } ?: emptyList()
                trySend(clients)
            }
        awaitClose { subscription.remove() }
    }

    override suspend fun addClient(client: Client): Result<String> {
        return try {
            val docRef = firestore.collection("clients").document()
            docRef.set(client.toMap()).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateClient(client: Client): Result<Unit> {
        return try {
            firestore.collection("clients").document(client.id)
                .update(client.toMap()).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
