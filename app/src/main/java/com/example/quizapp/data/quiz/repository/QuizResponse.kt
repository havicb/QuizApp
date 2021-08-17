package com.example.quizapp.data.quiz.repository

import com.google.gson.annotations.SerializedName

data class QuizResponse(
    @SerializedName("quizzes") val quizList: List<QuizDTO>
)
