package com.example.quizapp.data.questions.repository

import com.example.quizapp.core.Either
import com.example.quizapp.core.Failure
import com.example.quizapp.data.questions.dto.QuestionResponse

interface QuestionRepository {
    suspend fun fetchQuestions(
        amount: Int,
        category: Int,
        difficulty: String,
        type: String
    ): Either<Failure, QuestionResponse>
}
