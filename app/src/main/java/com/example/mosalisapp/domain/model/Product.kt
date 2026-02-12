package com.example.mosalisapp.domain.model


data class Product(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val quantity: Int = 0,
    val currency: String = "USD", // CDF ou USD
    val category: String = "",
    val businessId: String = "" // Clé de liaison indispensable
)