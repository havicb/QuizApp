package com.example.quizapp.data.base

import com.example.quizapp.data.ErrorResponse
import com.example.quizapp.domain.common.BaseResult
import retrofit2.Response

abstract class BaseRepository {

    internal suspend inline fun <reified T> Response<T>.getResults(): BaseResult<T, ErrorResponse> {
        return when (isSuccessful) {
            true -> BaseResult.Success(body() ?: T::class.java.newInstance())
            false -> BaseResult.Error(ErrorResponse(code(), message()))
        }
    }
}
