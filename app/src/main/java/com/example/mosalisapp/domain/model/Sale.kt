package com.example.mosalisapp.domain.model

data class Sale(
    val id: String = "",
    val customerName: String = "",
    val items: List<CartItem> = emptyList(),
    val totalAmount: Double = 0.0,
    val currency: String = "USD", // La devise principale de la transaction
    val sellerId: String = "",
    val businessId: String? = "",
    val timestamp: Long = System.currentTimeMillis()
)

data class CartItem(
    val productId: String = "",
    val productName: String = "",
    val quantity: Int = 0,
    val unitPrice: Double = 0.0,
    val subTotal: Double = 0.0
)