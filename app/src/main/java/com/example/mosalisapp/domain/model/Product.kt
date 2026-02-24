package com.example.mosalisapp.domain.model

data class Product(
    val id: String = "",
    val businessId: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val currency: String = "USD", // CDF ou USD
    val quantity: Int = 0,
    val imageUrl: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
