package com.example.mosalisapp.domain.model

data class Sale(
    val id: String = "",
    val businessId: String = "",
    val productId: String = "",
    val quantity: Int = 0,
    val totalAmount: Double = 0.0,
    val createdBy: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
