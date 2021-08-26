package com.example.quizapp.domain.quiz.entity

import com.example.quizapp.data.quiz.dto.QuizLocalDTO
import com.example.quizapp.presentation.main.home.QuizView

data class QuizEntity(
    val title: String,
    val difficulties: List<String>,
    val photoPath: String
)

fun QuizLocalDTO.toDomain() = QuizEntity(
    category,
    difficulties.split(','),
    photoPath
)

fun QuizEntity.toView() = QuizView(title, photoPath)
