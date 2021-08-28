package com.example.quizapp.data.questions.repository

import com.example.quizapp.data.ErrorResponse
import com.example.quizapp.data.questions.dto.QuestionResponse
import com.example.quizapp.domain.common.BaseResult

interface QuestionRepository {
    suspend fun fetchQuestions(
        amount: Int,
        category: Int,
        difficulty: String,
        type: String
    ): BaseResult<QuestionResponse, ErrorResponse>
}
