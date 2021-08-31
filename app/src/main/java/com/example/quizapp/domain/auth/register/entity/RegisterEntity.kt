package com.example.quizapp.domain.auth.register.entity

import com.example.quizapp.data.auth.register.dto.RegisterResponse

data class RegisterEntity(val authToken: String?, val message: String, val success: Boolean)

fun RegisterResponse.toEntity() = RegisterEntity(authToken, message, success)
