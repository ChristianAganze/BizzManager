package com.app.businessmanager.domain.model

data class Expense(
    val id: String = "",
    val businessId: String = "",
    val title: String = "",
    val amount: Double = 0.0,
    val createdBy: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
