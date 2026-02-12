package com.app.businessmanager.domain.model

data class User(
    val id: String = "",
    val businessId: String = "",
    val name: String = "",
    val email: String = "",
    val role: Role = Role.WORKER,
    val permissions: Map<String, Boolean> = emptyMap()
)

enum class Role {
    OWNER, WORKER
}
