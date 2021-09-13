package com.example.quizapp.domain.questions.usecase

import com.example.quizapp.core.Either
import com.example.quizapp.core.Failure
import com.example.quizapp.core.map
import com.example.quizapp.data.questions.dto.QuestionResponse
import com.example.quizapp.data.questions.repository.QuestionRepository
import com.example.quizapp.domain.base.Interactor
import com.example.quizapp.domain.questions.entity.QuestionEntity
import com.example.quizapp.domain.questions.entity.toDomain
import com.example.quizapp.presentation.main.quiz.QuizCategory
import com.example.quizapp.presentation.main.quiz.QuizDifficulty
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(
    private val questionRepository: QuestionRepository
) : Interactor<List<QuestionEntity>, Params>() {
    override suspend fun run(params: Params): Either<Failure, List<QuestionEntity>> {
        return questionRepository.fetchQuestions(
            params.amount,
            params.category,
            params.difficulty,
            "multiple"
        ).map { it.toEntity() }
    }
}


data class Params(
    val amount: Int,
    val category: Int,
    val difficulty: String
)


fun QuestionResponse.toEntity(): List<QuestionEntity> {
    return questions.map {
        it.toDomain()
    }
}