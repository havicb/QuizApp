package com.example.quizapp.data.questions.repository

import com.example.quizapp.data.ErrorResponse
import com.example.quizapp.data.questions.api.QuestionsAPI
import com.example.quizapp.domain.common.BaseResult
import com.example.quizapp.domain.questions.entity.QuestionEntity
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
    ): Flow<BaseResult<List<QuestionEntity>, ErrorResponse>> {
        return flow {
            val response = questionsApi.fetchQuestions(amount, category, difficulty, type)
            if (response.isSuccessful) {
                emit(
                    BaseResult.Success(
                        response.body()?.questions!!.map {
                            // I know this job is for domain layer, I will improve this little bit later
                            it.toDomain()
                        }
                    )
                )
            } else {
                emit(BaseResult.Error(ErrorResponse(response.code(), response.message())))
            }
        }
    }
}
