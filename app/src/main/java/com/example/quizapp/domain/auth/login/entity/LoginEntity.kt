package com.example.quizapp.domain.auth.login.entity

import com.example.quizapp.data.auth.login.dto.LoginResponse

data class LoginEntity(val authToken: String?, val message: String, val success: Boolean)

fun LoginResponse.toEntity() = LoginEntity(
    authToken, message, success
)
