package com.example.quizapp.data.quiz.repository

import android.content.Context
import com.example.quizapp.data.quiz.dto.QuizLocalResponse
import com.google.gson.Gson

class QuizRepositoryImpl(
    private val context: Context
) : QuizRepository {

    // get assets is only working when you have reference to a specific context
    override fun fetchQuizzes(): QuizLocalResponse {
        return Gson().fromJson(
            context.assets.open("quizes.json").bufferedReader(),
            QuizLocalResponse::class.java
        )
    }
}
