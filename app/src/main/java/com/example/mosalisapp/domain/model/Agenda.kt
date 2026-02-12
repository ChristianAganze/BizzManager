package com.example.mosalisapp.domain.model

data class Agenda(
    val agendaId: String = "",
    val businessId: String = "",
    val productId: String = "",
    val quantity: Int = 0,
    val supplierName: String = "",
    val plannedDate: Long = System.currentTimeMillis(),
    val notes: String? = null
)
