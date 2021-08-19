package com.example.quizapp.data.quiz.local

interface QuizLocalRepository {
    fun fetchQuizzes(): QuizLocalResponse
}
