package com.example.quizapp.core

import com.google.gson.annotations.SerializedName

// Base class for handling failures
sealed class Failure {
        abstract class NetworkFailure : Failure()
        object ServerFailure : NetworkFailure()
        object NotFound : NetworkFailure()
        object Forbidden : NetworkFailure()
        object NotAuthorized : NetworkFailure()
        data class BadRequest(val message: String) : NetworkFailure()
        data class NetworkConnectionFailure(var message: String): NetworkFailure()
    data class OtherFailure(val message: String) : Failure()
}



