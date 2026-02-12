package com.example.mosalisapp.domain.repository

import com.example.mosalisapp.domain.model.Agenda
import kotlinx.coroutines.flow.Flow

interface AgendaRepository {
    suspend fun addEvent(event: Agenda)
    fun getEvents(businessId: String): Flow<List<Agenda>>
}
