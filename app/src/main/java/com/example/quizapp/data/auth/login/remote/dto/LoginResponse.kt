package com.example.quizapp.data.auth.login.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("data") val authToken: String?,
    @SerializedName("message") val message: String,
    @SerializedName("success") val success: Boolean
)
