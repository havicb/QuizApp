package com.example.quizapp.data.quiz.dto

import com.google.gson.annotations.SerializedName

data class QuizLocalDTO(
    @SerializedName("category") val category: String,
    @SerializedName("difficulties") val difficulties: String,
    @SerializedName("photo") val photoPath: String
)
