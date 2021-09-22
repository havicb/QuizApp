package com.example.quizapp.presentation.main.quiz

import android.view.View
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.quizapp.R
import com.example.quizapp.core.Failure
import com.example.quizapp.domain.questions.entity.QuestionEntity
import com.example.quizapp.domain.questions.usecase.GetQuestionsUseCase
import com.example.quizapp.domain.questions.usecase.Params
import com.example.quizapp.presentation.base.view.BaseViewModel
import com.example.quizapp.presentation.main.home.QuizSettings
import com.example.quizapp.presentation.main.quiz.question.QuestionView
import com.example.quizapp.presentation.main.quiz.question.Selectable
import com.example.quizapp.presentation.main.quiz.question.toView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Stack
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
        fetch()
    }

    fun checkQuestion(userAnswer: String) = viewModelScope.launch {
        if (userAnswer == currentQuestion?.correctAnswer) {
            correctAnswer()
            return@launch
        }
        incorrectAnswer()
    }

    fun selectedAnswer(view: View) {
        changeState(QuizFragmentState.AnswerUnSelected)
        changeState(QuizFragmentState.AnswerSelected(SelectedAnswerModelUI(view as TextView)))
    }

    fun onLastQuestion() {
        changeState(QuizFragmentState.LastQuestion)
    }

    fun onQuizFinished() {
        navigate(QuizFragmentDirections.actionQuizFragmentToHomeFragment())
    }

    private suspend fun correctAnswer() {
        delay(1000)
        changeState(QuizFragmentState.CorrectAnswer())
        delay(1000)
        points += 2
        nextQuestion()
    }

    private suspend fun incorrectAnswer() {
        delay(1000)
        changeState(QuizFragmentState.InCorrectAnswer())
        delay(2000)
        changeState(QuizFragmentState.FinishedQuiz(points))
    }

    private fun fetch() = viewModelScope.launch {
        questionsUseCase(questionParams()).fold(::handleFailure, ::handleQuestions)
    }

    private fun questionParams(): Params {
        return Params(
            quizSettings!!.numberOfQuestions,
            quizSettings!!.category.apiValue,
            quizSettings!!.difficulty.lowercase()
        )
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.NetworkFailure -> handleCommonNetworkErrors(failure, ::fetch)
            else -> error.value = Failure.OtherFailure("Dummy error..")
        }
    }

    private fun handleQuestions(questions: List<QuestionEntity>) {
        for (i in (questions.size - 1) downTo 0) {
            questionsData.add(questions[i])
        }
        handleNewQuestion()
    }

    private fun nextQuestion() {
        changeState(QuizFragmentState.AnswerUnSelected)
        handleNewQuestion()
        if (questionsData.empty()) {
            changeState(QuizFragmentState.LastQuestion)
        }
    }

    private fun handleNewQuestion() {
        val question = questionsData.pop()
        changeState(QuizFragmentState.QuestionLoaded(10 - questionsData.size))
        updateAnswer(correctAnswer, question.correctAnswer)
        nextQuestion(question)
    }

    private fun nextQuestion(nextQuestion: QuestionEntity) {
        currentQuestion = nextQuestion
        val questionView = nextQuestion.toView((10 - questionsData.size))
        updateQuestion(questionView)
        updateAnswers(questionView)
    }

    private fun updateAnswers(questionView: QuestionView) {
        updateAnswer(answerA, questionView.answerA)
        updateAnswer(answerB, questionView.answerB)
        updateAnswer(answerC, questionView.answerC)
        updateAnswer(answerD, questionView.answerD)
    }

    private fun updateQuestion(data: QuestionView) {
        question.value = data.title
        questionNumber.value = data.questionNumber
    }

    private fun updateAnswer(answer: MutableLiveData<String>, data: String) {
        answer.value = data
    }

    private fun changeState(state: QuizFragmentState) {
        _quizFragmentState.value = state
    }
}

sealed class QuizFragmentState {
    data class QuestionLoaded(val questionNumber: Int) : QuizFragmentState()
    data class AnswerSelected(val selectable: Selectable) : QuizFragmentState()
    data class CorrectAnswer(val color: Int = R.color.lightGreen) : QuizFragmentState()
    data class InCorrectAnswer(val color: Int = R.color.red) : QuizFragmentState()
    data class FinishedQuiz(val points: Int) : QuizFragmentState()
    data class ShowToast(val message: String) : QuizFragmentState()
    object AnswerUnSelected : QuizFragmentState()
    object LastQuestion : QuizFragmentState()
}

data class SelectedAnswerModelUI(val answer: TextView) : Selectable {
    override val textView = answer
    override val backgroundColor = R.color.yellow
}
