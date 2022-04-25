package com.example.quizapp.presentation.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.quizapp.core.Helpers
import com.example.quizapp.data.prefstore.PrefsStore
import com.example.quizapp.domain.quiz.entity.QuizEntity
import com.example.quizapp.domain.quiz.entity.toView
import com.example.quizapp.domain.quiz.usecase.GetQuizDataUseCase
import com.example.quizapp.presentation.base.view.BaseViewModel
import com.example.quizapp.presentation.main.quiz.QuizCategory
import com.example.quizapp.presentation.main.quiz.QuizDifficulty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.Serializable
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    quizDataUseCase: GetQuizDataUseCase,
    prefsStore: PrefsStore
) : BaseViewModel() {

    private lateinit var quizSelected: String
    private var searchTerm = ""

    private var allQuizzes: List<QuizEntity> = quizDataUseCase.fetchQuizData()

    private val quizzes = MutableLiveData<List<QuizView>>()
    private val selectedQuiz = MutableLiveData<String>()

    val observeQuizzes: LiveData<List<QuizView>> get() = quizzes
    val observeSelectedQuiz: LiveData<String> get() = selectedQuiz
    lateinit var observeUserPoints: LiveData<Int>

    init {
        quizzes.value = allQuizzes.map { it.toView() }
        viewModelScope.launch {
            observeUserPoints = prefsStore.getUserPoints().asLiveData()
        }
    }

    fun onSearchButton() {
        quizzes.value = if (searchTerm.isEmpty()) {
            allQuizzes.map { it.toView() }
        } else {
            allQuizzes.filter { quizEntity ->
                quizEntity.title.lowercase().contains(searchTerm.lowercase())
            }.map { it.toView() }
        }
    }

    fun onSearchTermChange(text: CharSequence) {
        searchTerm = text.toString()
    }

    fun quizSelected(quizTitle: String) {
        quizSelected = quizTitle
        selectedQuiz.value = quizTitle
    }

    fun startQuiz(difficulty: QuizDifficulty) {
        navigate(
            HomeFragmentDirections.actionHomeFragmentToQuizFragment(
                quizSettingsParam(
                    difficulty
                )
            )
        )
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
