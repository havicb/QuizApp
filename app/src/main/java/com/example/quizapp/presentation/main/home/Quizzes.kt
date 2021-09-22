package com.example.quizapp.presentation.main.home

interface Quizzes {
    fun getSingleQuizById(id: Int): QuizView
    fun getSize(): Int
    fun updateQuizzes(data: List<QuizView>)
}

class QuizzesImpl : Quizzes {

    private var quizzes: ArrayList<QuizView> = arrayListOf()

    override fun getSingleQuizById(id: Int): QuizView {
        return quizzes[id]
    }

    override fun getSize(): Int {
        return quizzes.size
    }

    override fun updateQuizzes(data: List<QuizView>) {
        quizzes.clear()
        quizzes.addAll(data)
    }
}
