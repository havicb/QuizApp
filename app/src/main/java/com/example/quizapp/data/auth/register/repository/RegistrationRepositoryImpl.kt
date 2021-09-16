package com.example.quizapp.data.auth.register.repository

import com.example.quizapp.core.Either
import com.example.quizapp.core.Failure
import com.example.quizapp.data.NetworkHandler
import com.example.quizapp.data.auth.register.api.RegistrationAPI
import com.example.quizapp.data.auth.register.dto.RegisterRequest
import com.example.quizapp.data.auth.register.dto.RegisterResponse
import com.example.quizapp.data.base.BaseRepository
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    networkHandler: NetworkHandler,
    private val api: RegistrationAPI
) : RegistrationRepository, BaseRepository(networkHandler) {
    override suspend fun register(registerRequest: RegisterRequest): Either<Failure, RegisterResponse> {
        return api.register(registerRequest).getResults()
    }
}
