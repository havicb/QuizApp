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

    private var quizzes: List<QuizEntity> = quizDataUseCase.fetchQuizData()
    private var quizSelected: String? = null

    private val _quiz = MutableLiveData<List<QuizView>>()
    private val _homeFragmentState = MutableLiveData<HomeFragmentState>()

    val quiz: LiveData<List<QuizView>> get() = _quiz
    val homeFragmentState: LiveData<HomeFragmentState> get() = _homeFragmentState

    init {
        _quiz.value = quizzes.map {
            it.toView()
        }
    }

    fun quizSelected(quizTitle: String) {
        quizSelected = quizTitle
        _homeFragmentState.value = HomeFragmentState.QuizSelected(quizTitle)
    }

    fun startQuiz(difficulty: QuizDifficulty) {
        navigate(
            HomeFragmentDirections.actionHomeFragmentToQuizFragment(
                QuizSettings(
                    Helpers.findCategoryByTitle(quizSelected!!),
                    difficulty.value,
                    10
                )
            )
        )
    }
}

sealed class HomeFragmentState {
    data class QuizSelected(val title: String) : HomeFragmentState()
}

data class QuizSettings(
    val category: QuizCategory,
    val difficulty: String,
    val numberOfQuestions: Int
) : Serializable
