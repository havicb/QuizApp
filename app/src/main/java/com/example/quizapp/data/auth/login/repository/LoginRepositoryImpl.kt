package com.example.quizapp.data.auth.login.repository

import com.example.quizapp.data.ErrorResponse
import com.example.quizapp.data.auth.login.api.LoginAPI
import com.example.quizapp.data.auth.login.dto.LoginRequest
import com.example.quizapp.data.auth.login.dto.LoginResponse
import com.example.quizapp.data.base.BaseRepository
import com.example.quizapp.domain.common.BaseResult
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val loginAPI: LoginAPI) : LoginRepository,
    BaseRepository() {
    override suspend fun login(loginRequest: LoginRequest): BaseResult<LoginResponse, ErrorResponse> {
        return loginAPI.loginUser(loginRequest).getResults()
    }
}
