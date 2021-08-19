package com.example.quizapp.presentation.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quizapp.domain.quiz.entity.QuizData
import com.example.quizapp.domain.quiz.entity.toView
import com.example.quizapp.domain.quiz.usecase.GetQuizDataUseCase
import com.example.quizapp.presentation.base.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.Serializable
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    quizDataUseCase: GetQuizDataUseCase
) : BaseViewModel() {

    private var quizData: QuizData = quizDataUseCase.fetchQuizData()

    private val _quiz = MutableLiveData<List<QuizView>>()
    private val _homeFragmentState = MutableLiveData<HomeFragmentState>()

    private var quizSelected: String? = null

    val quiz: LiveData<List<QuizView>> get() = _quiz
    val homeFragmentState: LiveData<HomeFragmentState> get() = _homeFragmentState

    init {
        _quiz.value = quizData.quizzes.map {
            it.toView()
        }
    }

    fun quizSelected(quizTitle: String) {
        quizSelected = quizTitle
        _homeFragmentState.value = HomeFragmentState.QuizSelected(quizTitle)
    }

    fun startQuiz(difficulty: QuizDifficulty) {
        _homeFragmentState.value = HomeFragmentState.StartQuiz(
            QuizSettings(
                quizSelected!!,
                difficulty.value,
                10
            )
        )
    }
}

sealed class HomeFragmentState {
    data class QuizSelected(val title: String) : HomeFragmentState()
    data class StartQuiz(val quizSettings: QuizSettings) : HomeFragmentState()
}

data class QuizSettings(
    val title: String,
    val quizDifficulty: String,
    val numberOfQuestions: Int
) : Serializable
