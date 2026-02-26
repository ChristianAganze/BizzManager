package com.example.mosalisapp.domain.model

data class CartItem(
    val product: Product,
    val quantity: Int = 1
) {
    val totalAmount: Double get() = product.price * quantity
}
