package com.example.quizapp.domain.questions.usecase

import com.example.quizapp.data.ErrorResponse
import com.example.quizapp.data.questions.remote.repository.QuestionRepository
import com.example.quizapp.domain.common.BaseResult
import com.example.quizapp.domain.questions.entity.QuestionData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(
    private val questionRepository: QuestionRepository
) {

    suspend fun fetchQuestionData(
        amount: Int,
        category: Int,
        difficulty: String
    ): Flow<BaseResult<QuestionData, ErrorResponse>> {
        return questionRepository.fetchQuestions(amount, category, difficulty, "multiple")
    }
}
