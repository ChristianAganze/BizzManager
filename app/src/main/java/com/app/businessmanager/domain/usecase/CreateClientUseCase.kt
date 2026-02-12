package com.app.businessmanager.domain.usecase

import com.app.businessmanager.domain.model.Client
import com.app.businessmanager.domain.repository.ClientRepository

class CreateClientUseCase(private val repository: ClientRepository) {
    suspend operator fun invoke(client: Client): Result<String> {
        return repository.addClient(client)
    }
}
