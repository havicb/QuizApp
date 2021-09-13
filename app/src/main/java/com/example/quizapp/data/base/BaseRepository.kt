package com.example.quizapp.data.base

import android.util.Log
import com.example.quizapp.core.Either
import com.example.quizapp.core.Failure
import retrofit2.Response

abstract class BaseRepository {

    internal suspend inline fun <reified T> Response<T>.getResults(): Either<Failure, T> {
        return try {
            when (isSuccessful) {
                true -> Either.Right(body() ?: T::class.java.newInstance())
                false -> { Either.Left(Failure.ServerFailure) }
            }
        } catch(ex: Exception) {
            Log.d("CALLING", "${ex.localizedMessage}")
            return Either.Left(Failure.ServerFailure)
        }
    }
}
