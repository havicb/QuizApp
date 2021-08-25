package com.example.quizapp.presentation.main.quiz

import android.view.View
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.quizapp.R
import com.example.quizapp.data.ErrorResponse
import com.example.quizapp.domain.common.BaseResult
import com.example.quizapp.domain.questions.entity.QuestionData
import com.example.quizapp.domain.questions.entity.QuestionEntity
import com.example.quizapp.domain.questions.usecase.GetQuestionsUseCase
import com.example.quizapp.presentation.base.view.BaseViewModel
import com.example.quizapp.presentation.main.home.QuizSettings
import com.example.quizapp.presentation.main.quiz.question.Selectable
import com.example.quizapp.presentation.main.quiz.question.toView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val questionsUseCase: GetQuestionsUseCase,
    state: SavedStateHandle
) : BaseViewModel() {

    private val questionsData = Stack<QuestionEntity>()
    private var currentQuestion: QuestionEntity? = null
    private var quizSettings: QuizSettings? = null

    private val _quizFragmentState = MutableLiveData<QuizFragmentState>()
    private var points: Int = 0

    val question = MutableLiveData<String>()
    val questionNumber = MutableLiveData<String>()
    val answerA = MutableLiveData<String>()
    val answerB = MutableLiveData<String>()
    val answerC = MutableLiveData<String>()
    val answerD = MutableLiveData<String>()

    val quizFragmentState: LiveData<QuizFragmentState> get() = _quizFragmentState
    val correctAnswer = MutableLiveData<String>()

    init {
        quizSettings = state.get<QuizSettings>("quizSettings")
        fetch(quizSettings!!)
    }

    private fun fetch(quizSettings: QuizSettings) = viewModelScope.launch {
        questionsUseCase.fetchQuestionData(
            quizSettings.numberOfQuestions,
            quizSettings.category.apiValue,
            quizSettings.difficulty.lowercase()
        ).onStart {
            showLoading()
        }.catch { ex ->
            hideLoading()
        }.collect { result ->
            hideLoading()
            when (result) {
                is BaseResult.Success -> handleQuestions(result.data)
                is BaseResult.Error -> handleError(result.response)
            }
        }
    }

    private fun hideLoading() {
        _quizFragmentState.value = QuizFragmentState.Loading(true)
    }

    private fun showLoading() {
        _quizFragmentState.value = QuizFragmentState.Loading(false)
    }

    private fun handleError(response: ErrorResponse) {
        // todo
    }

    private fun handleQuestions(data: QuestionData) {
        for (i in (data.questions.size - 1) downTo 0) {
            questionsData.add(data.questions[i])
        }
        handleNewQuestion(questionsData.pop())
    }

    fun checkQuestion(userAnswer: String) = viewModelScope.launch {
        if (userAnswer == currentQuestion?.correctAnswer) {
            delay(1000)
            changeState(QuizFragmentState.CorrectAnswer())
            delay(1000)
            points += 2
            nextQuestion()
        } else {
            delay(1000)
            changeState(QuizFragmentState.InCorrectAnswer())
            delay(2000)
            changeState(QuizFragmentState.FinishedQuiz(points))
        }
    }

    fun selectedAnswer(view: View) {
        changeState(QuizFragmentState.AnswerUnSelected)
        changeState(QuizFragmentState.AnswerSelected(SelectedAnswerModelUI(view as TextView)))
    }

    fun onLastQuestion() {
    }

    private fun nextQuestion() {
        changeState(QuizFragmentState.AnswerUnSelected)
        handleNewQuestion(questionsData.pop())
        if (questionsData.empty()) {
            changeState(QuizFragmentState.LastQuestion)
        }
    }

    private fun changeState(state: QuizFragmentState) {
        _quizFragmentState.value = state
    }

    private fun handleNewQuestion(nextQuestion: QuestionEntity) {
        _quizFragmentState.value = QuizFragmentState.QuestionLoaded(10 - questionsData.size)
        correctAnswer.value = nextQuestion.correctAnswer
        currentQuestion = nextQuestion
        val questionView = nextQuestion.toView((10 - questionsData.size))
        question.value = questionView.title
        questionNumber.value = questionView.questionNumber
        answerA.value = questionView.answerA
        answerB.value = questionView.answerB
        answerC.value = questionView.answerC
        answerD.value = questionView.answerD
    }
}

sealed class QuizFragmentState {
    data class Loading(val isLoading: Boolean) : QuizFragmentState()
    data class QuestionLoaded(val questionNumber: Int) : QuizFragmentState()
    object AnswerUnSelected : QuizFragmentState()
    data class AnswerSelected(val selectable: Selectable) : QuizFragmentState()
    data class CorrectAnswer(val color: Int = R.color.lightGreen) : QuizFragmentState()
    data class InCorrectAnswer(val color: Int = R.color.red) : QuizFragmentState()
    data class FinishedQuiz(val points: Int) : QuizFragmentState()
    object LastQuestion : QuizFragmentState()
}

data class SelectedAnswerModelUI(val answer: TextView) : Selectable {
    override val textView = answer
    override val backgroundColor = R.color.yellow
}
