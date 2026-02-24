package com.example.mosalisapp.data.mapper

import com.example.mosalisapp.domain.model.Expense


fun Map<String, Any?>.toExpense(id: String): Expense {
    return Expense(
        id = id,
        businessId = this["businessId"] as? String ?: "",
        title = this["title"] as? String ?: "",
        amount = (this["amount"] as? Number)?.toDouble() ?: 0.0,
        createdBy = this["createdBy"] as? String ?: "",
        createdAt = this["createdAt"] as? Long ?: 0L
    )
}

fun Expense.toMap(): Map<String, Any?> {
    return mapOf(
        "businessId" to businessId,
        "title" to title,
        "amount" to amount,
        "createdBy" to createdBy,
        "createdAt" to createdAt
    )
}
