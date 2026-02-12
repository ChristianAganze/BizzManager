package com.app.businessmanager.domain.model

data class Client(
    val id: String = "",
    val businessId: String = "",
    val name: String = "",
    val phone: String = "",
    val email: String = "",
    val imageUrl: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
