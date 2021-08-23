package com.example.quizapp.data.auth.login.remote.api

import com.example.quizapp.data.auth.login.remote.dto.LoginRequest
import com.example.quizapp.data.auth.login.remote.dto.LoginResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginAPI {

    @POST("login")
    fun loginAsync(
        @Body loginRequest: LoginRequest
    ): Deferred<Response<LoginResponse>>
}
