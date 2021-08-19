package com.example.quizapp.presentation.main.quiz.question

import android.os.Build
import android.text.Html
import com.example.quizapp.domain.questions.entity.QuestionEntity

data class QuestionView(
    val questionNumber: Int,
    val title: String,
    val answerA: String,
    val answerB: String,
    val answerC: String,
    val answerD: String
)

fun QuestionEntity.toView(): QuestionView {
    val questionNumber = 1
    val answerA = correctAnswer
    val answerB = incorrectAnswers[0]
    val answerC = incorrectAnswers[1]
    val answerD = incorrectAnswers[2]
    var decodedQuestion = ""
    if (Build.VERSION.SDK_INT >= 24) {
        decodedQuestion = Html.fromHtml(question, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        decodedQuestion = Html.fromHtml(question).toString()
    }
    return QuestionView(questionNumber, decodedQuestion, answerA, answerB, answerC, answerD)
}
