package com.example.quizapp.data.auth.login.repository

import com.example.quizapp.data.ErrorResponse
import com.example.quizapp.data.auth.login.dto.LoginRequest
import com.example.quizapp.domain.auth.login.entity.LoginEntity
import com.example.quizapp.domain.common.BaseResult
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(loginRequest: LoginRequest): Flow<BaseResult<LoginEntity, ErrorResponse>>
}
