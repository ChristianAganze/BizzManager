package com.app.businessmanager.domain.model

data class Product(
    val id: String = "",
    val businessId: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val quantity: Int = 0,
    val imageUrl: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
