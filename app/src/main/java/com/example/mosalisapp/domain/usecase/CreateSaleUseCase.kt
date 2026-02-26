package com.example.mosalisapp.domain.usecase

import com.example.mosalisapp.domain.model.Sale
import com.example.mosalisapp.domain.repository.ProductRepository
import com.example.mosalisapp.domain.repository.SaleRepository


class CreateSaleUseCase(
    private val saleRepository: SaleRepository,
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(sale: Sale): Result<String> {
        return try {
            val productResult = productRepository.getProduct(sale.productId)
            val product = productResult.getOrNull() ?: return Result.failure(Exception("Produit non trouvé"))

            if (product.quantity < sale.quantity) {
                return Result.failure(Exception("Stock insuffisant (${product.quantity} disponibles)"))
            }

            // Mettre à jour le stock
            val updatedProduct = product.copy(quantity = product.quantity - sale.quantity)
            val updateResult = productRepository.updateProduct(updatedProduct)
            
            if (updateResult.isFailure) {
                return Result.failure(updateResult.exceptionOrNull() ?: Exception("Erreur lors de la mise à jour du stock"))
            }

            saleRepository.createSale(sale)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
