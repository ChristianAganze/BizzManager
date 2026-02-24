package com.example.mosalisapp.data.mapper

import com.example.mosalisapp.domain.model.Business
import com.example.mosalisapp.domain.model.User


fun Map<String, Any?>.toBusiness(id: String): Business {
    return Business(
        id = id,
        name = this["name"] as? String ?: "",
        category = this["category"] as? String ?: "",
        address = this["address"] as? String ?: "",
        currency = this["currency"] as? String ?: "USD",
        phone = this["phone"] as? String ?: "",
        createdAt = this["createdAt"] as? Long ?: 0L
    )
}

fun Business.toMap(): Map<String, Any?> {
    return mapOf(
        "name" to name,
        "category" to category,
        "address" to address,
        "currency" to currency,
        "phone" to phone,
        "createdAt" to createdAt
    )
}
