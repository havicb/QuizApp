package com.example.quizapp.data.questions.repository

import com.example.quizapp.data.ErrorResponse
import com.example.quizapp.data.base.BaseRepository
import com.example.quizapp.data.questions.api.QuestionsAPI
import com.example.quizapp.data.questions.dto.QuestionResponse
import com.example.quizapp.domain.common.BaseResult
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(
    private val questionsApi: QuestionsAPI
) : QuestionRepository, BaseRepository() {
    override suspend fun fetchQuestions(
        amount: Int,
        category: Int,
        difficulty: String,
        type: String
    ): BaseResult<QuestionResponse, ErrorResponse> {
        return questionsApi.fetchQuestions(amount, category, difficulty, type).getResults()
    }
}
