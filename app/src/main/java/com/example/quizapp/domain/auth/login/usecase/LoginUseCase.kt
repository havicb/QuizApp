package com.example.quizapp.domain.auth.login.usecase

import com.example.quizapp.core.Either
import com.example.quizapp.core.Failure
import com.example.quizapp.core.map
import com.example.quizapp.data.auth.login.dto.LoginRequest
import com.example.quizapp.data.auth.login.repository.LoginRepository
import com.example.quizapp.domain.auth.login.entity.LoginEntity
import com.example.quizapp.domain.auth.login.entity.toEntity
import com.example.quizapp.domain.base.Interactor
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) :
    Interactor<LoginEntity, Params>() {
    override suspend fun run(params: Params): Either<Failure, LoginEntity> {
        return loginRepository.login(params.loginRequest).map {it.toEntity()}
    }
}

data class Params(val loginRequest: LoginRequest)
