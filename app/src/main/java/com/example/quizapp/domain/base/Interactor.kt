package com.example.quizapp.domain.base

import com.example.quizapp.core.Either
import com.example.quizapp.core.Failure

// base class for implementing interactors
abstract class Interactor<out T, in Params> where T : Any {
    abstract suspend fun run(params: Params): Either<Failure, T>
    suspend operator fun invoke(params: Params): Either<Failure, T> = run(params)
}