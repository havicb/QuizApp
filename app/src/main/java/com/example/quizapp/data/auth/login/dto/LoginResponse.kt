package com.example.quizapp.data.auth.login.dto

import com.google.gson.annotations.SerializedName

// Adding SerializedName annotation to every field is good practice for distinction between different layer objects.
data class LoginResponse(
    @SerializedName("data") val authToken: String?,
    @SerializedName("message") val message: String,
    @SerializedName("success") val success: Boolean
)
