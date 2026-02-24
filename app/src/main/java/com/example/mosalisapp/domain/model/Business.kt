package com.example.mosalisapp.domain.model

data class Business(
    val id: String = "",
    val name: String = "",
    val category: String = "",
    val address: String = "",
    val currency: String = "USD",
    val phone: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
