package com.app.businessmanager.domain.model

data class Debt(
    val id: String = "",
    val businessId: String = "",
    val clientId: String = "",
    val amount: Double = 0.0,
    val isPaid: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
