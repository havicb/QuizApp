package com.example.quizapp.data.questions.repository

import com.example.quizapp.data.ErrorResponse
import com.example.quizapp.domain.common.BaseResult
import com.example.quizapp.domain.questions.entity.QuestionEntity
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {
    suspend fun fetchQuestions(
        amount: Int,
        category: Int,
        difficulty: String,
        type: String
    ): Flow<BaseResult<List<QuestionEntity>, ErrorResponse>>
}
