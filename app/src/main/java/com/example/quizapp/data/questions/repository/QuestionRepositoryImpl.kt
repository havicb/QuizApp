package com.example.quizapp.data.questions.repository

import com.example.quizapp.core.Either
import com.example.quizapp.core.Failure
import com.example.quizapp.data.NetworkHandler
import com.example.quizapp.data.base.BaseRepository
import com.example.quizapp.data.questions.api.QuestionsAPI
import com.example.quizapp.data.questions.dto.QuestionResponse
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(
    networkHandler: NetworkHandler,
    private val questionsApi: QuestionsAPI
) : QuestionRepository, BaseRepository(networkHandler) {
    override suspend fun fetchQuestions(
        amount: Int,
        category: Int,
        difficulty: String,
        type: String
    ): Either<Failure, QuestionResponse> {
        return questionsApi.fetchQuestions(amount, category, difficulty, type).getResults()
    }
}
