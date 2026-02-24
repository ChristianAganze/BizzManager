package com.example.mosalisapp.domain.repository

import com.example.mosalisapp.domain.model.Client
import kotlinx.coroutines.flow.Flow

interface ClientRepository {
    fun getClients(businessId: String): Flow<List<Client>>
    suspend fun addClient(client: Client): Result<String>
    suspend fun updateClient(client: Client): Result<Unit>
}
