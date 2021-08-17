package com.example.quizapp.data.quiz.repository

import android.content.Context
import com.google.gson.Gson

class QuizRepositoryImpl(
    private val context: Context
) : QuizRepository {

    // get assets only works when you have reference to specific context
    override fun fetchQuizzes(): QuizResponse {
        return Gson().fromJson(
            context.assets.open("quizes.json").bufferedReader(),
            QuizResponse::class.java
        )
    }
}
