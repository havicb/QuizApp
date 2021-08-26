package com.example.quizapp.domain.quiz.usecase

import com.example.quizapp.data.quiz.repository.QuizRepository
import com.example.quizapp.domain.quiz.entity.QuizEntity
import com.example.quizapp.domain.quiz.entity.toDomain
import javax.inject.Inject

class GetQuizDataUseCase @Inject constructor(
    private val repository: QuizRepository
) {
    fun fetchQuizData(): List<QuizEntity> {
        return repository.fetchQuizzes().quizLocalList.map {
            it.toDomain()
        }
    }
}
