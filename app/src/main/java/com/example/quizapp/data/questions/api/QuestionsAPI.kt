package com.example.quizapp.data.questions.api

import com.example.quizapp.data.questions.dto.QuestionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestionsAPI {
    @GET("api.php")
    suspend fun fetchQuestions(
        @Query("amount") amount: Int,
        @Query("category") category: Int,
        @Query("difficulty") difficulty: String,
        @Query("type") type: String
    ): Response<QuestionResponse>
}
