package com.example.quizapp.data.auth.register.api

import com.example.quizapp.data.auth.register.dto.RegisterRequest
import com.example.quizapp.data.auth.register.dto.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationAPI {
    @POST("register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponse>
}
