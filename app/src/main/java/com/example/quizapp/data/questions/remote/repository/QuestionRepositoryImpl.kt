package com.example.quizapp.data.questions.remote.repository

import com.example.quizapp.data.ErrorResponse
import com.example.quizapp.data.questions.remote.api.QuestionsAPI
import com.example.quizapp.domain.common.BaseResult
import com.example.quizapp.domain.questions.entity.QuestionData
import com.example.quizapp.domain.questions.entity.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(
    private val questionsApi: QuestionsAPI
) : QuestionRepository {
    override suspend fun fetchQuestions(
        amount: Int,
        category: Int,
        difficulty: String,
        type: String
    ): Flow<BaseResult<QuestionData, ErrorResponse>> {
        return flow {
            val response = questionsApi.fetchQuestionsAsync(amount, category, difficulty, type)
            if (response.isSuccessful) {
                emit(
                    BaseResult.Success(
                        QuestionData(
                            response.body()?.questions!!.map {
                                // i know this job is for domain layer, I will improve this little bit later
                                it.toDomain()
                            }
                        )
                    )
                )
            } else {
                emit(BaseResult.Error(ErrorResponse(response.code(), response.message())))
            }
        }
    }
}
