package com.example.quizapp.presentation.main.quiz.question

import android.os.Build
import android.text.Html
import com.example.quizapp.domain.questions.entity.QuestionEntity

data class QuestionView(
    val questionNumber: String,
    val title: String,
    val answerA: String,
    val answerB: String,
    val answerC: String,
    val answerD: String
)

fun QuestionEntity.toView(questionNumber: Int): QuestionView {
    return getQuestionView(this, questionNumber, getRandomNumber())
}

private fun getRandomNumber(): Int {
    return (1..4).random()
}

private fun getQuestionView(
    questionEntity: QuestionEntity,
    questionNumber: Int,
    correctAnswerPosition: Int
): QuestionView = with(questionEntity) {
    return when (correctAnswerPosition) {
        1 -> QuestionView(
            "Question $questionNumber",
            decodeHtml(question),
            decodeHtml(correctAnswer),
            decodeHtml(incorrectAnswers[0]),
            decodeHtml(incorrectAnswers[1]),
            decodeHtml(incorrectAnswers[2])
        )
        2 -> QuestionView(
            "Question $questionNumber",
            decodeHtml(question),
            decodeHtml(incorrectAnswers[0]),
            decodeHtml(correctAnswer),
            decodeHtml(incorrectAnswers[1]),
            decodeHtml(incorrectAnswers[2])
        )
        3 -> QuestionView(
            "Question $questionNumber",
            decodeHtml(question),
            decodeHtml(incorrectAnswers[1]),
            decodeHtml(incorrectAnswers[0]),
            decodeHtml(correctAnswer),
            decodeHtml(incorrectAnswers[2])
        )
        else -> QuestionView(
            "Question $questionNumber",
            decodeHtml(question),
            decodeHtml(incorrectAnswers[2]),
            decodeHtml(incorrectAnswers[0]),
            decodeHtml(incorrectAnswers[1]),
            decodeHtml(correctAnswer)
        )
    }
}

private fun decodeHtml(string: String): String {
    return if (Build.VERSION.SDK_INT >= 24) {
        Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        Html.fromHtml(string).toString()
    }
}
