package com.app.businessmanager.domain.usecase

import com.app.businessmanager.domain.model.Client
import com.app.businessmanager.domain.repository.ClientRepository
import kotlinx.coroutines.flow.Flow

class GetClientsUseCase(private val repository: ClientRepository) {
    operator fun invoke(businessId: String): Flow<List<Client>> {
        return repository.getClients(businessId)
    }
}
