package com.example.quizapp.data.quiz.dto

import com.google.gson.annotations.SerializedName

data class QuizLocalResponse(
    @SerializedName("quizzes") val quizLocalList: List<QuizLocalDTO>
)
