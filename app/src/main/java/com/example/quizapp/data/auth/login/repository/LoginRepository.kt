package com.example.quizapp.data.auth.login.repository

import com.example.quizapp.data.ErrorResponse
import com.example.quizapp.data.auth.login.dto.LoginRequest
import com.example.quizapp.data.auth.login.dto.LoginResponse
import com.example.quizapp.domain.common.BaseResult

interface LoginRepository {
    suspend fun login(loginRequest: LoginRequest): BaseResult<LoginResponse, ErrorResponse>
}
