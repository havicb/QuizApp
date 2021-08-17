package com.example.quizapp.presentation.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quizapp.domain.quiz.entity.QuizData
import com.example.quizapp.domain.quiz.entity.toView
import com.example.quizapp.domain.quiz.usecase.GetQuizDataUseCase
import com.example.quizapp.presentation.common.base.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    quizDataUseCase: GetQuizDataUseCase
) : BaseViewModel() {

    private var quizData: QuizData = quizDataUseCase.fetchQuizData()

    private val _quiz = MutableLiveData<List<QuizView>>()
    val quiz: LiveData<List<QuizView>> get() = _quiz

    init {
        _quiz.value = quizData.quizzes.map {
            it.toView()
        }
    }
}