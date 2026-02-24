package com.example.mosalisapp.domain.usecase
import com.example.mosalisapp.domain.model.Client
import com.example.mosalisapp.domain.repository.ClientRepository
import kotlinx.coroutines.flow.Flow

class GetClientsUseCase(private val repository: ClientRepository) {
    operator fun invoke(businessId: String): Flow<List<Client>> {
        return repository.getClients(businessId)
    }
}
