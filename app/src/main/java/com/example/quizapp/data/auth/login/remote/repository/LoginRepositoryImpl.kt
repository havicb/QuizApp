package com.example.quizapp.data.auth.login.remote.repository

import com.example.quizapp.data.auth.login.remote.api.LoginAPI
import com.example.quizapp.data.auth.login.remote.dto.LoginRequest
import com.example.quizapp.data.auth.login.remote.dto.LoginResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val loginAPI: LoginAPI) : LoginRepository {
    override suspend fun loginAsync(loginRequest: LoginRequest): Deferred<Response<LoginResponse>> {
        return loginAPI.loginAsync(loginRequest)
    }
}
