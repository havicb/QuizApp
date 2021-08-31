package com.example.quizapp.data.auth.register.repository

import com.example.quizapp.data.ErrorResponse
import com.example.quizapp.data.auth.register.dto.RegisterRequest
import com.example.quizapp.data.auth.register.dto.RegisterResponse
import com.example.quizapp.domain.common.BaseResult

interface RegistrationRepository {
    suspend fun register(registerRequest: RegisterRequest): BaseResult<RegisterResponse, ErrorResponse>
}
