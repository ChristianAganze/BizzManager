package com.example.mosalisapp.data.mapper

import com.example.mosalisapp.domain.model.Product


fun Map<String, Any?>.toProduct(id: String): Product {
    return Product(
        id = id,
        businessId = this["businessId"] as? String ?: "",
        name = this["name"] as? String ?: "",
        category = this["category"] as? String ?: "",
        price = (this["price"] as? Number)?.toDouble() ?: 0.0,
        quantity = (this["quantity"] as? Number)?.toInt() ?: 0,
        imageUrl = this["imageUrl"] as? String,
        createdAt = (this["createdAt"] as? Number)?.toLong() ?: 0L
    )
}

fun Product.toMap(): Map<String, Any?> {
    return mapOf(
        "businessId" to businessId,
        "name" to name,
        "category" to category,
        "price" to price,
        "quantity" to quantity,
        "imageUrl" to imageUrl,
        "createdAt" to createdAt
    )
}
