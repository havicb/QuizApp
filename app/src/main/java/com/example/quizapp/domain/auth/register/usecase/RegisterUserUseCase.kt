package com.example.quizapp.domain.auth.register.usecase

import com.example.quizapp.data.ErrorResponse
import com.example.quizapp.data.auth.register.dto.RegisterRequest
import com.example.quizapp.data.auth.register.repository.RegistrationRepository
import com.example.quizapp.domain.auth.register.entity.RegisterEntity
import com.example.quizapp.domain.auth.register.entity.toEntity
import com.example.quizapp.domain.common.BaseResult
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val registrationRepository: RegistrationRepository
) {
    suspend fun register(registerRequest: RegisterRequest): BaseResult<RegisterEntity, ErrorResponse> {
        return when (val call = registrationRepository.register(registerRequest)) {
            is BaseResult.Success -> BaseResult.Success(call.data.toEntity())
            is BaseResult.Error -> {
                BaseResult.Error(call.response)
            }
        }
    }
}
