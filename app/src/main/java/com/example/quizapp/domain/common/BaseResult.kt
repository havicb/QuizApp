package com.example.quizapp.domain.common

sealed class BaseResult<out T, out U> {
    data class Error<out U>(val response: U) : BaseResult<Nothing, U>()
    data class Success<T>(var data: T) : BaseResult<T, Nothing>()
}
