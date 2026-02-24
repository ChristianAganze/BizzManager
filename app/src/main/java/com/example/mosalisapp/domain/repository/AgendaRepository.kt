package com.example.mosalisapp.domain.repository

import com.example.mosalisapp.domain.model.AgendaEvent
import kotlinx.coroutines.flow.Flow

interface AgendaRepository {
    fun getEvents(businessId: String): Flow<List<AgendaEvent>>
    suspend fun addEvent(event: AgendaEvent): Result<String>
}
