package com.example.quizapp.data.quiz.repository

import com.example.quizapp.data.quiz.dto.QuizLocalResponse

interface QuizRepository {
    fun fetchQuizzes(): QuizLocalResponse
}
