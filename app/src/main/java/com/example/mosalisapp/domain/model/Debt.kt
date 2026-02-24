package com.example.mosalisapp.domain.model

data class Debt(
    val id: String = "",
    val businessId: String = "",
    val customerName: String = "",
    val amount: Double = 0.0,
    val currency: String = "USD",
    val description: String = "",
    val isPaid: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
