package com.example.mosalisapp.data.mapper

import com.example.mosalisapp.domain.model.Role
import com.example.mosalisapp.domain.model.User


fun Map<String, Any?>.toUser(id: String): User {
    return User(
        id = id,
        businessId = this["businessId"] as? String ?: "",
        name = this["name"] as? String ?: "",
        email = this["email"] as? String ?: "",
        role = Role.valueOf(this["role"] as? String ?: "WORKER"),
        permissions = (this["permissions"] as? Map<String, Boolean>) ?: emptyMap()
    )
}

fun User.toMap(): Map<String, Any?> {
    return mapOf(
        "businessId" to businessId,
        "name" to name,
        "email" to email,
        "role" to role.name,
        "permissions" to permissions
    )
}
