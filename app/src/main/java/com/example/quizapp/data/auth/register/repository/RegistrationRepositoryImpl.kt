package com.example.quizapp.data.auth.register.repository

import com.example.quizapp.core.Either
import com.example.quizapp.core.Failure
import com.example.quizapp.data.auth.register.api.RegistrationAPI
import com.example.quizapp.data.auth.register.dto.RegisterRequest
import com.example.quizapp.data.auth.register.dto.RegisterResponse
import com.example.quizapp.data.base.BaseRepository
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val api: RegistrationAPI
) : RegistrationRepository, BaseRepository() {
    override suspend fun register(registerRequest: RegisterRequest): Either<Failure, RegisterResponse> {
        return api.register(registerRequest).getResults()
    }
}
