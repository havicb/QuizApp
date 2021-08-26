package com.example.quizapp.data.questions.dto

import com.google.gson.annotations.SerializedName

// Purpose of DTO is to fetch data from remote source. Since every call to the remote source
// is expensive DTO should bring as much data as possible
data class QuestionDTO(
    @SerializedName("category")
    val category: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("difficulty")
    val difficulty: String,
    @SerializedName("question")
    val question: String,
    @SerializedName("correct_answer")
    val correctAnswer: String,
    @SerializedName("incorrect_answers")
    val incorrectAnswers: List<String>? = null
)
