package com.example.quizapp.data.quiz.repository

import com.google.gson.annotations.SerializedName

data class QuizDTO(
    @SerializedName("category") val category: String,
    @SerializedName("difficulties") val difficulties: String,
    @SerializedName("photo") val photoPath: String
)
