package com.example.quizapp.core

// Base class for handling failures
sealed class Failure {
    abstract class NetworkFailure(val message: String) : Failure()
    class ServerFailure(message: String) : NetworkFailure(message)
    class NotFound(message: String) : NetworkFailure(message)
    class NotAuthorized(message: String) : NetworkFailure(message)
    class Forbidden(message: String) : NetworkFailure(message)
    class BadRequest(message: String) : NetworkFailure(message)
    class NetworkConnectionFailure(message: String) : NetworkFailure(message)

    data class OtherFailure(val message: String) : Failure()
}
