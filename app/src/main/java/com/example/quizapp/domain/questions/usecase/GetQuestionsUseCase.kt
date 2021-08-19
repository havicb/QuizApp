package com.example.quizapp.domain.questions.usecase

import com.example.quizapp.data.questions.remote.repository.QuestionRepository
import com.example.quizapp.domain.questions.entity.QuestionData
import com.example.quizapp.domain.questions.entity.toDomain
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(
    private val questionRepository: QuestionRepository
) {

    suspend fun fetchQuestionData(
        amount: Int,
        category: Int,
        difficulty: String
    ): QuestionData {
        val result = questionRepository.fetchQuestions(amount, category, difficulty, "multiple").await()
        return result.body()!!.toDomain()
    }
}
