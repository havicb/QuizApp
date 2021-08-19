package com.example.quizapp.domain.questions.entity

import com.example.quizapp.data.questions.remote.dto.QuestionDTO
import com.example.quizapp.data.questions.remote.dto.QuestionResponse

data class QuestionEntity(
    val question: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>
)

data class QuestionData(
    val questions: List<QuestionEntity>
)

fun QuestionDTO.toDomain() = QuestionEntity(question, correctAnswer, incorrectAnswers!!)

fun QuestionResponse.toDomain() = QuestionData(
    questions.map {
        it.toDomain()
    }
)
