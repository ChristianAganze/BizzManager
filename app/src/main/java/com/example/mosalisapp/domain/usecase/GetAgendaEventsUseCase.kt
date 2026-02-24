package com.example.mosalisapp.domain.usecase

import com.example.mosalisapp.domain.model.AgendaEvent
import com.example.mosalisapp.domain.repository.AgendaRepository
import kotlinx.coroutines.flow.Flow


class GetAgendaEventsUseCase(private val repository: AgendaRepository) {
    operator fun invoke(businessId: String): Flow<List<AgendaEvent>> {
        return repository.getEvents(businessId)
    }
}
