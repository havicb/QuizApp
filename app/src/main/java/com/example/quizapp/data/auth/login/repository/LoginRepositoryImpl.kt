package com.example.quizapp.data.auth.login.repository

import com.example.quizapp.data.ErrorResponse
import com.example.quizapp.data.auth.login.api.LoginAPI
import com.example.quizapp.data.auth.login.dto.LoginRequest
import com.example.quizapp.domain.auth.login.entity.LoginEntity
import com.example.quizapp.domain.common.BaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val loginAPI: LoginAPI) : LoginRepository {

    override suspend fun login(loginRequest: LoginRequest): Flow<BaseResult<LoginEntity, ErrorResponse>> {
        return flow {
            val response = loginAPI.loginUser(loginRequest)
            if (response.isSuccessful) {
                val body = response.body()
                emit(BaseResult.Success(LoginEntity(body!!.authToken, body.message, body.success)))
            } else {
                emit(BaseResult.Error(ErrorResponse(response.code(), response.message())))
            }
        }
    }
}
