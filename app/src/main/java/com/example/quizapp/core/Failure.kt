package com.example.quizapp.core

// Base class for handling failures
sealed class Failure {
    object ServerFailure : Failure()
}