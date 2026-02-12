package com.example.mosalisapp.domain.model


data class Expense(
    val id: String = "",
    val title: String = "",
    val amount: Double = 0.0,
    val currency: String = "USD",
    val category: String = "Autre", // Ex: Loyer, Transport, Salaire, Stock
    val description: String = "",
    val businessId: String? = "",
    val timestamp: Long = System.currentTimeMillis()
)