package com.example.quizapp.domain.questions.usecase

import com.example.quizapp.data.ErrorResponse
import com.example.quizapp.data.questions.repository.QuestionRepository
import com.example.quizapp.domain.common.BaseResult
import com.example.quizapp.domain.questions.entity.QuestionEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(
    private val questionRepository: QuestionRepository
) {

    suspend fun fetchQuestionData(
        amount: Int,
        category: Int,
        difficulty: String
    ): Flow<BaseResult<List<QuestionEntity>, ErrorResponse>> {
        return questionRepository.fetchQuestions(amount, category, difficulty, "multiple")
    }
}
