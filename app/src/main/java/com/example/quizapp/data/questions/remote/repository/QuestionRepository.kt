package com.example.quizapp.data.questions.remote.repository

import com.example.quizapp.data.questions.remote.dto.QuestionResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response

interface QuestionRepository {
    suspend fun fetchQuestions(amount: Int, category: Int, difficulty: String, type: String): Deferred<Response<QuestionResponse>>
}
