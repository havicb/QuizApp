package com.example.quizapp.domain.quiz.usecase

import com.example.quizapp.data.quiz.local.QuizLocalRepository
import com.example.quizapp.domain.quiz.entity.QuizData
import com.example.quizapp.domain.quiz.entity.toDomain
import javax.inject.Inject

class GetQuizDataUseCase @Inject constructor(
    private val localRepository: QuizLocalRepository
) {
    fun fetchQuizData(): QuizData {
        return localRepository.fetchQuizzes().toDomain()
    }
}
