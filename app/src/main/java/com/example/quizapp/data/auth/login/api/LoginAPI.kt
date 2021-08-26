package com.example.quizapp.data.auth.login.api

import com.example.quizapp.data.auth.login.dto.LoginRequest
import com.example.quizapp.data.auth.login.dto.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginAPI {
    @POST("login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>
}
