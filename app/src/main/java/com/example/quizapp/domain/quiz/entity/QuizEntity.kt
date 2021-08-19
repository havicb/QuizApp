package com.example.quizapp.domain.quiz.entity

import com.example.quizapp.data.quiz.local.QuizLocalDTO
import com.example.quizapp.data.quiz.local.QuizLocalResponse
import com.example.quizapp.presentation.main.home.QuizView

data class QuizEntity(
    val title: String,
    val difficulties: List<String>,
    val photoPath: String
)

data class QuizData(
    val quizzes: List<QuizEntity>
)

fun QuizLocalDTO.toDomain() = QuizEntity(
    category,
    difficulties.split(','),
    photoPath
)

fun QuizLocalResponse.toDomain() = QuizData(
    quizLocalList.map {
        it.toDomain()
    }
)

fun QuizEntity.toView() = QuizView(title, photoPath)
