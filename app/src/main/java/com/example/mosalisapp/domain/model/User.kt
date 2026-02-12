package com.example.mosalisapp.domain.model


data class User(
    val userId: String = "",
    val name: String = "",
    val email: String = "",
    val role: String = "OWNER", // "OWNER" ou "WORKER"
    val businessId: String? = null, // Sera rempli après la création de l'entreprise
    val permissions: Map<String, Boolean> = mapOf(
        "sell" to (role == "OWNER"),
        "manageStock" to (role == "OWNER"),
        "manageExpenses" to (role == "OWNER")
    )
)

