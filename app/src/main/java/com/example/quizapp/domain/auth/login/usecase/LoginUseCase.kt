package com.example.quizapp.domain.auth.login.usecase

import com.example.quizapp.data.auth.login.remote.dto.LoginRequest
import com.example.quizapp.data.auth.login.remote.dto.LoginResponse
import com.example.quizapp.data.auth.login.remote.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {

    suspend fun loginUser(loginRequest: LoginRequest): LoginResponse {
        val response = loginRepository.loginAsync(loginRequest).await()
        return if (response.isSuccessful) {
            response.body()!!
        } else {
            LoginResponse(null, "Not authenticated", false)
        }
    }
}
