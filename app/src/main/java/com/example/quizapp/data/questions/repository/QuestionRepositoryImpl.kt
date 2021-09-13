package com.example.quizapp.data.questions.repository

import com.example.quizapp.core.Either
import com.example.quizapp.core.Failure
import com.example.quizapp.data.base.BaseRepository
import com.example.quizapp.data.questions.api.QuestionsAPI
import com.example.quizapp.data.questions.dto.QuestionResponse
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(
    private val questionsApi: QuestionsAPI
) : QuestionRepository, BaseRepository() {
    override suspend fun fetchQuestions(
        amount: Int,
        category: Int,
        difficulty: String,
        type: String
    ): Either<Failure, QuestionResponse> {
        return questionsApi.fetchQuestions(amount, category, difficulty, type).getResults()
    }
}
