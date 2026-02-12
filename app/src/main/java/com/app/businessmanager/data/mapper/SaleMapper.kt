package com.app.businessmanager.data.mapper

import com.app.businessmanager.domain.model.Sale

fun Map<String, Any?>.toSale(id: String): Sale {
    return Sale(
        id = id,
        businessId = this["businessId"] as? String ?: "",
        productId = this["productId"] as? String ?: "",
        quantity = (this["quantity"] as? Number)?.toInt() ?: 0,
        totalAmount = (this["totalAmount"] as? Number)?.toDouble() ?: 0.0,
        createdBy = this["createdBy"] as? String ?: "",
        createdAt = this["createdAt"] as? Long ?: 0L
    )
}

fun Sale.toMap(): Map<String, Any?> {
    return mapOf(
        "businessId" to businessId,
        "productId" to productId,
        "quantity" to quantity,
        "totalAmount" to totalAmount,
        "createdBy" to createdBy,
        "createdAt" to createdAt
    )
}
