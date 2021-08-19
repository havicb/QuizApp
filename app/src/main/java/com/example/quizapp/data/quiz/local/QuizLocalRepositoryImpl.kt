package com.example.quizapp.data.quiz.local

import android.content.Context
import com.google.gson.Gson

class QuizLocalRepositoryImpl(
    private val context: Context
) : QuizLocalRepository {

    // get assets only works when you have reference to specific context
    override fun fetchQuizzes(): QuizLocalResponse {
        return Gson().fromJson(
            context.assets.open("quizes.json").bufferedReader(),
            QuizLocalResponse::class.java
        )
    }
}
