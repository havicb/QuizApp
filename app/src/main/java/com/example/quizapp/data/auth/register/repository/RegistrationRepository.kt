package com.example.quizapp.data.auth.register.repository

import com.example.quizapp.core.Either
import com.example.quizapp.core.Failure
import com.example.quizapp.data.auth.register.dto.RegisterRequest
import com.example.quizapp.data.auth.register.dto.RegisterResponse

interface RegistrationRepository {
    suspend fun register(registerRequest: RegisterRequest): Either<Failure, RegisterResponse>
}
