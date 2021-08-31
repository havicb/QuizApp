package com.example.quizapp.data.auth.register.dto

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("data") val authToken: String?,
    @SerializedName("message") val message: String,
    @SerializedName("success") val success: Boolean
)
