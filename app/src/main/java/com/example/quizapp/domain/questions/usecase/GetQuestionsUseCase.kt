package com.example.quizapp.domain.questions.usecase

import com.example.quizapp.data.ErrorResponse
import com.example.quizapp.data.questions.repository.QuestionRepository
import com.example.quizapp.domain.common.BaseResult
import com.example.quizapp.domain.questions.entity.QuestionEntity
import com.example.quizapp.domain.questions.entity.toDomain
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(
    private val questionRepository: QuestionRepository
) {

    suspend fun fetchQuestionData(
        amount: Int,
        category: Int,
        difficulty: String
    ): BaseResult<List<QuestionEntity>, ErrorResponse> {
        return when (
            val call =
                questionRepository.fetchQuestions(amount, category, difficulty, "multiple")
        ) {
            is BaseResult.Success -> BaseResult.Success(call.data.questions.map { it.toDomain() })
            is BaseResult.Error -> BaseResult.Error(call.response)
        }
    }
}

//         
