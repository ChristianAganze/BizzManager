package com.example.mosalisapp.data.repository

import com.example.mosalisapp.domain.model.User
import com.example.mosalisapp.domain.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl(
    private val firestore: FirebaseFirestore
) : UserRepository {

    override fun saveUser(user: User) {
        firestore.collection("users")
            .document(user.userId)
            .set(user)
    }

    override suspend fun getUser(uid: String): User? {
        return try {
            val document = firestore.collection("users").document(uid).get().await()
            document.toObject(User::class.java)
        } catch (e: Exception) {
            null
        }
    }

    // --- AJOUT DES MÉTHODES MANQUANTES ---

    override suspend fun getWorkersByBusiness(businessId: String): List<User> {
        return try {
            firestore.collection("users")
                .whereEqualTo("businessId", businessId)
                .whereEqualTo("role", "WORKER")
                .get()
                .await()
                .toObjects(User::class.java)
        } catch (e: Exception) {
            emptyList() // Retourne une liste vide en cas d'erreur
        }
    }

    override suspend fun createWorker(worker: User): Result<Unit> {
        return try {
            firestore.collection("users")
                .document(worker.userId)
                .set(worker)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}