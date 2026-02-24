package com.example.mosalisapp.data.repository

import com.example.mosalisapp.data.mapper.toMap
import com.example.mosalisapp.data.mapper.toUser
import com.example.mosalisapp.domain.model.Role
import com.example.mosalisapp.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import com.example.mosalisapp.domain.model.User

class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override fun getCurrentUser(): Flow<User?> = callbackFlow {

        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->

            val firebaseUser = firebaseAuth.currentUser

            if (firebaseUser == null) {

                trySend(null)

            } else {

                val registration = firestore.collection("users")
                    .document(firebaseUser.uid)
                    .addSnapshotListener { snapshot, error ->

                        if (error != null) return@addSnapshotListener

                        val user = snapshot?.data?.toUser(firebaseUser.uid)

                        trySend(user)
                    }
            }
        }

        auth.addAuthStateListener(listener)

        awaitClose {
            auth.removeAuthStateListener(listener)
        }
    }

    override suspend fun login(email: String, password: String): Result<User?> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val userId = authResult.user?.uid ?: return Result.failure(Exception("User not found"))
            val snapshot = firestore.collection("users").document(userId).get().await()
            val user = snapshot.data?.toUser(userId)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateBusinessId(userId: String, businessId: String): Result<Unit> {
        return try {
            firestore.collection("users").document(userId)
                .update("businessId", businessId).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        auth.signOut()
    }

    override suspend fun register(name: String, email: String, password: String): Result<User> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = authResult.user?.uid ?: return Result.failure(Exception("User ID null"))
            val user = User(id = userId, name = name, email = email, role = Role.OWNER, businessId = userId)
            
            firestore.collection("users").document(userId).set(user.toMap()).await()
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun registerWorker(name: String, email: String, password: String, businessId: String): Result<User> {
        // Implementation detail: for this setup we simulate success or use a dedicated method
        // In a real app, this might call a Cloud Function
        return try {
            val user = User(id = "worker_${System.currentTimeMillis()}", name = name, email = email, role = Role.WORKER, businessId = businessId)
            firestore.collection("users").document(user.id).set(user.toMap()).await()
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
