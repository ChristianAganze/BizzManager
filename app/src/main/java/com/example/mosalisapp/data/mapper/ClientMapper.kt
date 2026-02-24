package com.example.mosalisapp.data.mapper

import com.example.mosalisapp.domain.model.Client


fun Map<String, Any?>.toClient(id: String): Client {
    return Client(
        id = id,
        businessId = this["businessId"] as? String ?: "",
        name = this["name"] as? String ?: "",
        phone = this["phone"] as? String ?: "",
        email = this["email"] as? String ?: "",
        imageUrl = this["imageUrl"] as? String,
        createdAt = this["createdAt"] as? Long ?: 0L
    )
}

fun Client.toMap(): Map<String, Any?> {
    return mapOf(
        "businessId" to businessId,
        "name" to name,
        "phone" to phone,
        "email" to email,
        "imageUrl" to imageUrl,
        "createdAt" to createdAt
    )
}
