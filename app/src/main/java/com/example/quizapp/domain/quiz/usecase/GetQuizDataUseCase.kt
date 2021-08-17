package com.example.quizapp.domain.quiz.usecase

import com.example.quizapp.data.quiz.repository.QuizRepository
import com.example.quizapp.domain.quiz.entity.QuizData
import com.example.quizapp.domain.quiz.entity.toDomain
import javax.inject.Inject

class GetQuizDataUseCase @Inject constructor(
    private val repository: QuizRepository
) {
    fun fetchQuizData(): QuizData {
        return repository.fetchQuizzes().toDomain()
    }
}
