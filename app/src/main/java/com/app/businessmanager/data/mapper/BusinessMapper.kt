package com.app.businessmanager.data.mapper

import com.app.businessmanager.domain.model.Business

fun Map<String, Any?>.toBusiness(id: String): Business {
    return Business(
        id = id,
        name = this["name"] as? String ?: "",
        createdAt = this["createdAt"] as? Long ?: 0L
    )
}

fun Business.toMap(): Map<String, Any?> {
    return mapOf(
        "name" to name,
        "createdAt" to createdAt
    )
}
