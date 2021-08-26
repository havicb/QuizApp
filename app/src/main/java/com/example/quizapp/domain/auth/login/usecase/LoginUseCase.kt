package com.example.quizapp.domain.auth.login.usecase

import com.example.quizapp.data.ErrorResponse
import com.example.quizapp.data.auth.login.dto.LoginRequest
import com.example.quizapp.data.auth.login.repository.LoginRepository
import com.example.quizapp.domain.auth.login.entity.LoginEntity
import com.example.quizapp.domain.common.BaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend fun loginUser(loginRequest: LoginRequest): Flow<BaseResult<LoginEntity, ErrorResponse>> {
        return loginRepository.login(loginRequest)
    }
}
