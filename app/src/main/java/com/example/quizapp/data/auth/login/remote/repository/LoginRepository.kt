package com.example.quizapp.data.auth.login.remote.repository

import com.example.quizapp.data.auth.login.remote.dto.LoginRequest
import com.example.quizapp.data.auth.login.remote.dto.LoginResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response

interface LoginRepository {
    suspend fun loginAsync(loginRequest: LoginRequest): Deferred<Response<LoginResponse>>
}
