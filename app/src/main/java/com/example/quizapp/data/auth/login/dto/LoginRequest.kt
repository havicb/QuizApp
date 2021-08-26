package com.example.quizapp.data.auth.login.dto

import com.google.gson.annotations.SerializedName

// Adding SerializedName annotation to every field is good practice for distinction between different layer objects.
data class LoginRequest(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)
