package com.example.quizapp.data.questions.remote.repository

import com.example.quizapp.data.questions.remote.api.QuestionsAPI
import com.example.quizapp.data.questions.remote.dto.QuestionResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(
    private val questionsApi: QuestionsAPI
) : QuestionRepository {
    override suspend fun fetchQuestions(
        amount: Int,
        category: Int,
        difficulty: String,
        type: String
    ): Deferred<Response<QuestionResponse>> {
        return questionsApi.fetchQuestionsAsync(amount, category, difficulty, type)
    }
}
