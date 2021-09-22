package com.example.quizapp.data.base

import com.example.quizapp.core.Either
import com.example.quizapp.core.Failure
import com.example.quizapp.data.NetworkHandler
import retrofit2.Response
import java.net.HttpURLConnection
import java.net.SocketTimeoutException

abstract class BaseRepository(private val networkHandler: NetworkHandler) {

    internal inline fun <reified T> Response<T>.getResults(): Either<Failure, T> {
        return try {
            when (isSuccessful) {
                true -> Either.Right(body() ?: T::class.java.newInstance())
                false -> handleFailedResponse(code(), message())
            }
        } catch (ex: Exception) {
            when (ex) {
                is SocketTimeoutException -> Either.Left(Failure.NetworkConnectionFailure("Connection timed out"))
                else -> Either.Left(Failure.OtherFailure("Unknown error.. Try again in few minutes!"))
            }
        }
    }

    private fun handleFailedResponse(
        code: Int,
        message: String
    ): Either.Left<Failure.NetworkFailure> {
        return when (code) {
            HttpURLConnection.HTTP_INTERNAL_ERROR -> Either.Left(Failure.ServerFailure(message))
            HttpURLConnection.HTTP_BAD_REQUEST -> Either.Left(Failure.BadRequest(message))
            HttpURLConnection.HTTP_NOT_FOUND -> Either.Left(Failure.NotFound(message))
            HttpURLConnection.HTTP_FORBIDDEN -> Either.Left(Failure.Forbidden(message))
            HttpURLConnection.HTTP_UNAUTHORIZED -> Either.Left(Failure.NotAuthorized(message))
            else -> throw UnknownError()
        }
    }
}
