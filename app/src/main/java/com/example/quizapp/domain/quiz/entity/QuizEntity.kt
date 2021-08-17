package com.example.quizapp.domain.quiz.entity

import com.example.quizapp.data.quiz.repository.QuizDTO
import com.example.quizapp.data.quiz.repository.QuizResponse
import com.example.quizapp.presentation.main.home.QuizView

data class QuizEntity(
    val title: String,
    val difficulties: List<String>,
    val photoPath: String
)

data class QuizData(
    val quizzes: List<QuizEntity>
)

fun QuizDTO.toDomain() = QuizEntity(
    category,
    difficulties.split(','),
    photoPath
)

fun QuizResponse.toDomain() = QuizData(
    quizList.map {
        it.toDomain()
    }
)

fun QuizEntity.toView() = QuizView(title, photoPath)
