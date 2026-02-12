/*
package com.example.mosalisapp.domain.useCase

import com.example.mosalisapp.domain.model.Business
import com.example.mosalisapp.domain.model.User
import com.example.mosalisapp.domain.repository.BusinessRepository
import com.example.mosalisapp.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import java.util.UUID

class CreateCompanyUseCase(
    private val auth: FirebaseAuth,
    private val companyRepository: BusinessRepository,
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        name: String,
        phoneCampany: String,
        category: String,
        address: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val uid = auth.currentUser?.uid
            ?: return onError("Utilisateur non connecté")

        val companyId = UUID.randomUUID().toString()

        val business = Business(
            businessId = companyId,
            name = name,
            type = category,
            address = address,
            phone = phoneCampany,
            ownerId = uid
        )

        companyRepository.createCampany(business, {
            userRepository.saveUser(
                User(
                    userId = uid,
                    name = name,
                    email = auth.currentUser?.email ?: "",
                    businessId =  companyId,

                )
            )
            onSuccess()
        }, onError)
    }
}
*/
