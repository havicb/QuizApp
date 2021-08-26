package com.example.quizapp.domain.questions.entity

import com.example.quizapp.data.questions.dto.QuestionDTO

data class QuestionEntity(
    val question: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>
)

fun QuestionDTO.toDomain() = QuestionEntity(question, correctAnswer, incorrectAnswers!!)
