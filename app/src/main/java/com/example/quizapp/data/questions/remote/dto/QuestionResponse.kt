package com.example.quizapp.data.questions.remote.dto

import com.google.gson.annotations.SerializedName

data class QuestionResponse(
    @SerializedName("response_code")
    val responseCode: Int,
    @SerializedName("results")
    val questions: List<QuestionDTO>
)

