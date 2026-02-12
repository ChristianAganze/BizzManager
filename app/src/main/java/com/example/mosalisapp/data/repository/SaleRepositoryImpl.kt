package com.example.mosalisapp.data.repository

import com.example.mosalisapp.domain.model.Sale
import com.example.mosalisapp.domain.repository.SaleRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class SaleRepositoryImpl(
    private val firestore: FirebaseFirestore
) : SaleRepository {

    override suspend fun addSale(sale: Sale): Result<Unit> {
        return try {
            val batch = firestore.batch()

            // 1. Créer le document de vente (Facture globale)
            val saleRef = firestore.collection("sales").document()
            batch.set(saleRef, sale.copy(id = saleRef.id))

            // 2. Mettre à jour le stock pour CHAQUE article dans le panier
            for (item in sale.items) {
                val productRef = firestore.collection("products").document(item.productId)

                // On récupère le stock actuel pour vérifier la disponibilité
                val productDoc = productRef.get().await()
                val currentQty = productDoc.getLong("quantity")?.toInt() ?: 0

                if (currentQty >= item.quantity) {
                    // Déduire la quantité vendue
                    batch.update(productRef, "quantity", currentQty - item.quantity)
                } else {
                    // Si un seul produit manque, on annule tout le processus
                    return Result.failure(Exception("Stock insuffisant pour le produit: ${item.productName}"))
                }
            }

            // 3. Exécuter toutes les opérations en une seule fois
            batch.commit().await()
            Result.success(Unit)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getSalesByBusiness(businessId: String): List<Sale> = try {
        firestore.collection("sales")
            .whereEqualTo("businessId", businessId)
            .get().await().toObjects(Sale::class.java)
            .sortedByDescending { it.timestamp }
    } catch (e: Exception) {
        emptyList()
    }
}