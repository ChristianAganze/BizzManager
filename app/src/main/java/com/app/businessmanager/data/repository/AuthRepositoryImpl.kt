package com.app.businessmanager.data.repository

import com.app.businessmanager.data.mapper.toUser
import com.app.businessmanager.domain.model.Role
import com.app.businessmanager.domain.model.User
import com.app.businessmanager.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override fun getCurrentUser(): Flow<User?> = callbackFlow {
        val listener = auth.addAuthStateListener { firebaseAuth ->
            val firebaseUser = firebaseAuth.currentUser
            if (firebaseUser == null) {
                trySend(null)
            } else {
                firestore.collection("users").document(firebaseUser.uid)
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) return@addSnapshotListener
                        val user = snapshot?.data?.toUser(firebaseUser.uid)
                        trySend(user)
                    }
            }
        }
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    override suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
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
