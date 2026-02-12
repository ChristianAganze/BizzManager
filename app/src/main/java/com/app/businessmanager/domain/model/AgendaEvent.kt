package com.app.businessmanager.domain.model

data class AgendaEvent(
    val id: String = "",
    val businessId: String = "",
    val title: String = "",
    val description: String = "",
    val date: Long = System.currentTimeMillis()
)
