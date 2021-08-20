package com.example.quizapp.core

import com.example.quizapp.presentation.main.quiz.QuizCategory

class Helpers {
    companion object {
        fun findCategoryByTitle(title: String): QuizCategory {
            QuizCategory.values().forEach {
                if (title.lowercase() == it.category.lowercase()) {
                    return it
                }
            }
            return QuizCategory.GENERAL_KNOWLEDGE
        }
    }
}
