package com.example.quizapp.data.auth.register.repository

import com.example.quizapp.data.ErrorResponse
import com.example.quizapp.data.auth.register.api.RegistrationAPI
import com.example.quizapp.data.auth.register.dto.RegisterRequest
import com.example.quizapp.data.auth.register.dto.RegisterResponse
import com.example.quizapp.data.base.BaseRepository
import com.example.quizapp.domain.common.BaseResult
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val api: RegistrationAPI
) : RegistrationRepository, BaseRepository() {
    override suspend fun register(registerRequest: RegisterRequest): BaseResult<RegisterResponse, ErrorResponse> {
        return api.register(registerRequest).getResults()
    }
}
