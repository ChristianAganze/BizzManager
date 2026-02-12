package com.app.businessmanager.domain.usecase

import com.app.businessmanager.domain.model.AgendaEvent
import com.app.businessmanager.domain.repository.AgendaRepository
import kotlinx.coroutines.flow.Flow

class GetAgendaEventsUseCase(private val repository: AgendaRepository) {
    operator fun invoke(businessId: String): Flow<List<AgendaEvent>> {
        return repository.getEvents(businessId)
    }
}
