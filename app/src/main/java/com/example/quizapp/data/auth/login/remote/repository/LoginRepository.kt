package com.example.quizapp.data.auth.login.remote.repository

import com.example.quizapp.data.ErrorResponse
import com.example.quizapp.data.auth.login.remote.dto.LoginRequest
import com.example.quizapp.data.auth.login.remote.dto.LoginResponse
import com.example.quizapp.domain.auth.login.entity.LoginEntity
import com.example.quizapp.domain.common.BaseResult
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface LoginRepository {
    suspend fun login(loginRequest: LoginRequest): Flow<BaseResult<LoginEntity, ErrorResponse>>
}
