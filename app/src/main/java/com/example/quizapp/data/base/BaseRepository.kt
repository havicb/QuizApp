package com.example.quizapp.data.base

import android.util.Log
import com.example.quizapp.core.Either
import com.example.quizapp.core.Failure
import com.example.quizapp.data.NetworkHandler
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.http.HTTP
import java.net.HttpURLConnection
import java.net.SocketTimeoutException

abstract class BaseRepository(private val networkHandler: NetworkHandler) {

    internal inline fun <reified T> Response<T>.getResults(): Either<Failure, T> {
        if (!networkHandler.isNetworkAvailable()) {
            return Either.Left(Failure.NetworkConnectionFailure("No internet available!"))
        }
        return try {
            when (isSuccessful) {
                true -> Either.Right(body() ?: T::class.java.newInstance())
                false -> {
                    val type = object : TypeToken<NetworkErrorResponse>() {}.type
                    val err: NetworkErrorResponse =
                        Gson().fromJson(errorBody()!!.charStream(), type)
                    handleFailedResponse(code(), err.message)
                }
            }
        } catch (ex: Exception) {
            when (ex) {
                is SocketTimeoutException -> Either.Left(Failure.NetworkConnectionFailure("Connection timed out"))
                else -> throw ex
            }
        }
    }

    private fun handleFailedResponse(
        code: Int,
        message: String
    ): Either.Left<Failure.NetworkFailure> {
        return when (code) {
            HttpURLConnection.HTTP_INTERNAL_ERROR -> Either.Left(Failure.ServerFailure)
            HttpURLConnection.HTTP_BAD_REQUEST -> Either.Left(Failure.BadRequest(message))
            HttpURLConnection.HTTP_NOT_FOUND -> Either.Left(Failure.NotFound)
            HttpURLConnection.HTTP_FORBIDDEN -> Either.Left(Failure.Forbidden)
            HttpURLConnection.HTTP_UNAUTHORIZED -> Either.Left(Failure.NotAuthorized)
            else -> throw UnknownError()
        }
    }
}

data class NetworkErrorResponse(
    @SerializedName("message")
    open var message: String = "",
    @SerializedName("success") var success: Boolean = false
)
