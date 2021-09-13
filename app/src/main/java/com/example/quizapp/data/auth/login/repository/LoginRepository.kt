package com.example.quizapp.data.auth.login.repository

import com.example.quizapp.core.Either
import com.example.quizapp.core.Failure
import com.example.quizapp.data.auth.login.dto.LoginRequest
import com.example.quizapp.data.auth.login.dto.LoginResponse

interface LoginRepository {
    suspend fun login(loginRequest: LoginRequest): Either<Failure, LoginResponse>
}
