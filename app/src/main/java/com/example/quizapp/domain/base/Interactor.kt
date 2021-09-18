package com.example.quizapp.domain.base

import android.util.Log
import com.example.quizapp.core.Either
import com.example.quizapp.core.Failure
import com.example.quizapp.data.NetworkHandler
import javax.inject.Inject

// base class for implementing interactors
abstract class Interactor<out T, in Params> where T : Any {

    @Inject
    lateinit var networkHandler: NetworkHandler

    abstract suspend fun run(params: Params): Either<Failure, T>
    suspend operator fun invoke(params: Params): Either<Failure, T> {
        if (!networkHandler.isNetworkAvailable()) {
            return Either.Left(Failure.NetworkConnectionFailure("No internet available!"))
        }
        return run(params)
    }
}