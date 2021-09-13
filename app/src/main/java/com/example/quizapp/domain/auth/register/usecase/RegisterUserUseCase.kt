package com.example.quizapp.domain.auth.register.usecase

import com.example.quizapp.core.Either
import com.example.quizapp.core.Failure
import com.example.quizapp.core.map
import com.example.quizapp.data.auth.register.dto.RegisterRequest
import com.example.quizapp.data.auth.register.repository.RegistrationRepository
import com.example.quizapp.domain.auth.register.entity.RegisterEntity
import com.example.quizapp.domain.auth.register.entity.toEntity
import com.example.quizapp.domain.base.Interactor
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val registrationRepository: RegistrationRepository
) : Interactor<RegisterEntity, Params>() {
    override suspend fun run(params: Params): Either<Failure, RegisterEntity> {
        return registrationRepository.register(params.registerRequest).map {it.toEntity()}
    }
}

data class Params(val registerRequest: RegisterRequest)
