package com.example.mosalisapp.data.repository

import com.example.mosalisapp.domain.model.User
import com.example.mosalisapp.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.UUID

class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override suspend fun login(email: String, pass: String): Result<User> = try {
        val result = auth.signInWithEmailAndPassword(email, pass).await()
        val uid = result.user?.uid ?: throw Exception("Échec de connexion")
        val userDoc = firestore.collection("users").document(uid).get().await()
        val user = userDoc.toObject(User::class.java) ?: throw Exception("Données invalides")
        Result.success(user)
    } catch (e: Exception) { Result.failure(e) }

    override suspend fun signUpOwner(email: String, pass: String, userName: String): Result<String> = try {
        val res = auth.createUserWithEmailAndPassword(email, pass).await()
        val uid = res.user!!.uid
        val user = User(userId = uid, name = userName, email = email, role = "OWNER")
        firestore.collection("users").document(uid).set(user).await()
        Result.success(uid)
    } catch (e: Exception) { Result.failure(e) }

// Dans AuthRepositoryImpl.kt

    override suspend fun createWorker(
        email: String,
        pass: String,
        name: String,
        businessId: String
    ): Result<Unit> = try {
        // 1. CRÉATION DU COMPTE RÉEL DANS FIREBASE AUTH
        // On crée l'utilisateur pour qu'il puisse se connecter plus tard
        val authResult = auth.createUserWithEmailAndPassword(email, pass).await()
        val workerId = authResult.user?.uid ?: throw Exception("Erreur lors de la création Auth")

        // 2. STOCKAGE DES INFOS DANS FIRESTORE
        val worker = User(
            userId = workerId,
            name = name,
            email = email,
            role = "WORKER",
            businessId = businessId
        )
        firestore.collection("users").document(workerId).set(worker).await()

        // 3. LE PIÈGE : Firebase vient de connecter le travailleur.
        // On déconnecte le travailleur pour que l'Owner puisse se reconnecter
        // ou pour que le travailleur puisse se loguer proprement.
        auth.signOut()

        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
    override suspend fun getWorkersByBusiness(businessId: String): List<User> = try {
        firestore.collection("users")
            .whereEqualTo("businessId", businessId)
            .whereEqualTo("role", "WORKER")
            .get().await()
            .toObjects(User::class.java)
    } catch (e: Exception) { emptyList() }

    override fun getCurrentUser(): User? {
        val fbUser = auth.currentUser ?: return null
        return User(userId = fbUser.uid, email = fbUser.email ?: "", name = "")
    }

    override fun logout(): Result<Unit> = try {
        auth.signOut()
        Result.success(Unit)
    } catch (e: Exception) { Result.failure(e) }
}