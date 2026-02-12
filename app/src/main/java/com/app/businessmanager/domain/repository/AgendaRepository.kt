package com.app.businessmanager.domain.repository

import com.app.businessmanager.domain.model.AgendaEvent
import kotlinx.coroutines.flow.Flow

interface AgendaRepository {
    fun getEvents(businessId: String): Flow<List<AgendaEvent>>
    suspend fun addEvent(event: AgendaEvent): Result<String>
}
