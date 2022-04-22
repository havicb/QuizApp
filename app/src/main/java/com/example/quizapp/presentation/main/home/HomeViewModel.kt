package com.example.quizapp.presentation.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quizapp.core.Helpers
import com.example.quizapp.domain.quiz.entity.QuizEntity
import com.example.quizapp.domain.quiz.entity.toView
import com.example.quizapp.domain.quiz.usecase.GetQuizDataUseCase
import com.example.quizapp.presentation.base.view.BaseViewModel
import com.example.quizapp.presentation.main.quiz.QuizCategory
import com.example.quizapp.presentation.main.quiz.QuizDifficulty
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.Serializable
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    quizDataUseCase: GetQuizDataUseCase
) : BaseViewModel() {

    private lateinit var quizSelected: String

    private var quizzes: List<QuizEntity> = quizDataUseCase.fetchQuizData()

    private val quiz = MutableLiveData<List<QuizView>>()
    private val selectedQuiz = MutableLiveData<String>()

    val observeQuizzes: LiveData<List<QuizView>> get() = quiz
    val observeSelectedQuiz: LiveData<String> get() = selectedQuiz

    init {
        quiz.value = quizzes.map { it.toView() }
    }

    fun quizSelected(quizTitle: String) {
        quizSelected = quizTitle
        selectedQuiz.value = quizTitle
    }

    fun startQuiz(difficulty: QuizDifficulty) {
        navigate(HomeFragmentDirections.actionHomeFragmentToQuizFragment(quizSettingsParam(difficulty)))
    }

    private fun quizSettingsParam(difficulty: QuizDifficulty): QuizSettings {
        return QuizSettings(Helpers.findCategoryByTitle(quizSelected), difficulty.value, 10)
    }
}

data class QuizSettings(
    val category: QuizCategory,
    val difficulty: String,
    val numberOfQuestions: Int
) : Serializable
