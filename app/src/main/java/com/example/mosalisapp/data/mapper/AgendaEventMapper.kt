package com.example.mosalisapp.data.mapper

import com.example.mosalisapp.domain.model.AgendaEvent


fun Map<String, Any?>.toAgendaEvent(id: String): AgendaEvent {
    return AgendaEvent(
        id = id,
        businessId = this["businessId"] as? String ?: "",
        title = this["title"] as? String ?: "",
        description = this["description"] as? String ?: "",
        date = this["date"] as? Long ?: 0L
    )
}

fun AgendaEvent.toMap(): Map<String, Any?> {
    return mapOf(
        "businessId" to businessId,
        "title" to title,
        "description" to description,
        "date" to date
    )
}
