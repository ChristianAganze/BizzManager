package com.example.mosalisapp.data.mapper

import com.example.mosalisapp.domain.model.Debt


fun Map<String, Any?>.toDebt(id: String): Debt {
    return Debt(
        id = id,
        businessId = this["businessId"] as? String ?: "",
        clientId = this["clientId"] as? String ?: "",
        amount = (this["amount"] as? Number)?.toDouble() ?: 0.0,
        isPaid = this["isPaid"] as? Boolean ?: false,
        createdAt = this["createdAt"] as? Long ?: 0L
    )
}

fun Debt.toMap(): Map<String, Any?> {
    return mapOf(
        "businessId" to businessId,
        "clientId" to clientId,
        "amount" to amount,
        "isPaid" to isPaid,
        "createdAt" to createdAt
    )
}
