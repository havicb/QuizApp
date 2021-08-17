package com.example.quizapp.data.quiz.repository

interface QuizRepository {
    fun fetchQuizzes(): QuizResponse
}
