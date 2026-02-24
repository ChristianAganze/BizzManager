package com.example.mosalisapp.domain.usecase

import com.example.mosalisapp.domain.model.Client
import com.example.mosalisapp.domain.repository.ClientRepository

class CreateClientUseCase(private val repository: ClientRepository) {
    suspend operator fun invoke(client: Client): Result<String> {
        return repository.addClient(client)
    }
}
