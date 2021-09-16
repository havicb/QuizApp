package com.example.quizapp.data.auth.login.repository

import com.example.quizapp.core.Either
import com.example.quizapp.core.Failure
import com.example.quizapp.data.NetworkHandler
import com.example.quizapp.data.auth.login.api.LoginAPI
import com.example.quizapp.data.auth.login.dto.LoginRequest
import com.example.quizapp.data.auth.login.dto.LoginResponse
import com.example.quizapp.data.base.BaseRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    networkHandler: NetworkHandler,
    private val loginAPI: LoginAPI
) : LoginRepository,
    BaseRepository(networkHandler) {
    override suspend fun login(loginRequest: LoginRequest): Either<Failure, LoginResponse> {
        return loginAPI.loginUser(loginRequest).getResults()
    }
}
