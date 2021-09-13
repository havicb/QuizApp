package com.example.quizapp.core

sealed class Failure {
    object ServerFailure : Failure()
}