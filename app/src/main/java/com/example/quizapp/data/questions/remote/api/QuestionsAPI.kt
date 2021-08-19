package com.example.quizapp.data.questions.remote.api

import com.example.quizapp.data.questions.remote.dto.QuestionResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestionsAPI {
    @GET("api.php")
    fun fetchQuestionsAsync(
        @Query("amount") amount: Int,
        @Query("category") category: Int,
        @Query("difficulty") difficulty: String,
        @Query("type") type: String
    ): Deferred<Response<QuestionResponse>>
}
