package com.app.businessmanager.domain.model

data class Business(
    val id: String = "",
    val name: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
