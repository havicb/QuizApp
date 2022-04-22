package com.example.quizapp.presentation.main.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.quizapp.core.Failure
import com.example.quizapp.domain.questions.entity.QuestionEntity
import com.example.quizapp.domain.questions.usecase.GetQuestionsUseCase
import com.example.quizapp.domain.questions.usecase.Params
import com.example.quizapp.presentation.base.view.BaseViewModel
import com.example.quizapp.presentation.main.home.QuizSettings
import com.example.quizapp.presentation.main.quiz.question.QuestionView
import com.example.quizapp.presentation.main.quiz.question.toView
import com.example.quizapp.presentation.main.components.AnswerView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
    private var points: Int = 0
    private var selectedAnswer: Answers? = null
        set(value) {
            if(field != value && value != null) {
                onFieldChange(value)
            }
            field = value
        }

    private var userAnswer: String = ""

    private val _quizFragmentState = MutableLiveData<QuizFragmentState>()
    private val firstAnswerState = MutableLiveData<AnswerView.State>()
    private val secondAnswerState = MutableLiveData<AnswerView.State>()
    private val thirdAnswerState = MutableLiveData<AnswerView.State>()
    private val fourthAnswerState = MutableLiveData<AnswerView.State>()

    val question = MutableLiveData<String>()
    val questionNumber = MutableLiveData<String>()
    val answerA = MutableLiveData<String>()
    val answerB = MutableLiveData<String>()
    val answerC = MutableLiveData<String>()
    val answerD = MutableLiveData<String>()

    val observeQuizScreenState: LiveData<QuizFragmentState> = _quizFragmentState
    val observeFirstAnswerState: LiveData<AnswerView.State> = firstAnswerState
    val observeSecondAnswerState: LiveData<AnswerView.State> = secondAnswerState
    val observeThirdAnswerState: LiveData<AnswerView.State> = thirdAnswerState
    val observeFourthAnswerState: LiveData<AnswerView.State> = fourthAnswerState
    val observeButtonSelected =  MutableLiveData<Unit>()

    init {
        quizSettings = state.get<QuizSettings>("quizSettings")
        fetch()
    }

    fun checkQuestion() = viewModelScope.launch {
        observeButtonSelected.value = Unit
        if (isAnswerTrue()) {
            onCorrectAnswer()
        } else {
            onIncorrectAnswer()
        }
    }

    fun onFirstAnswerSelected() {
        deselectAnswer()
        selectedAnswer = Answers.A
    }

    fun onSecondAnswerSelected() {
        deselectAnswer()
        selectedAnswer = Answers.B
    }

    fun onThirdAnswerSelected() {
        deselectAnswer()
        selectedAnswer = Answers.C
    }

    fun onFourthAnswerSelected() {
        deselectAnswer()
        selectedAnswer = Answers.D
    }

    fun onLastQuestion() {
        changeState(QuizFragmentState.LastQuestion)
    }

    fun onQuizFinished() {
        navigate(QuizFragmentDirections.actionQuizFragmentToHomeFragment())
    }

    private fun onFieldChange(value: Answers) {
        when (value) {
            Answers.A -> {
                firstAnswerState.value = AnswerView.State.SELECTED
                userAnswer = answerA.value.toString()
            }
            Answers.B -> {
                secondAnswerState.value = AnswerView.State.SELECTED
                userAnswer = answerB.value.toString()
            }
            Answers.C -> {
                thirdAnswerState.value = AnswerView.State.SELECTED
                userAnswer = answerC.value.toString()
            }
            Answers.D -> {
                fourthAnswerState.value = AnswerView.State.SELECTED
                userAnswer = answerD.value.toString()
            }
        }
    }

    private fun deselectAnswer() {
        if (selectedAnswer == null) {
            return
        }
        when (selectedAnswer) {
            Answers.A -> firstAnswerState.value = AnswerView.State.DEFAULT
            Answers.B -> secondAnswerState.value = AnswerView.State.DEFAULT
            Answers.C -> thirdAnswerState.value = AnswerView.State.DEFAULT
            Answers.D -> fourthAnswerState.value = AnswerView.State.DEFAULT
        }
        selectedAnswer = null
    }

    private fun isAnswerTrue(): Boolean {
        return userAnswer.lowercase() == currentQuestion?.correctAnswer?.lowercase()
    }

    private suspend fun onCorrectAnswer() {
        delay(1000)
        updateCorrectAnswer()
        delay(1000)
        points += 2
        nextQuestion()
    }

    private suspend fun onIncorrectAnswer() {
        delay(1000)
        updateIncorrectAnswer()
        delay(2000)
        changeState(QuizFragmentState.FinishedQuiz(points))
    }

    private fun updateCorrectAnswer() {
        when (selectedAnswer) {
            Answers.A -> firstAnswerState.value = AnswerView.State.TRUE_ANSWER
            Answers.B -> secondAnswerState.value = AnswerView.State.TRUE_ANSWER
            Answers.C -> thirdAnswerState.value = AnswerView.State.TRUE_ANSWER
            Answers.D -> fourthAnswerState.value = AnswerView.State.TRUE_ANSWER
        }
    }

    private fun updateIncorrectAnswer() {
        when (selectedAnswer) {
            Answers.A -> firstAnswerState.value = AnswerView.State.WRONG_ANSWER
            Answers.B -> secondAnswerState.value = AnswerView.State.WRONG_ANSWER
            Answers.C -> thirdAnswerState.value = AnswerView.State.WRONG_ANSWER
            Answers.D -> fourthAnswerState.value = AnswerView.State.WRONG_ANSWER
        }
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
            else -> observeError.value = Failure.OtherFailure("Dummy error..")
        }
    }

    private fun handleQuestions(questions: List<QuestionEntity>) {
        for (i in (questions.size - 1) downTo 0) {
            questionsData.add(questions[i])
        }
        handleNewQuestion()
    }

    private fun nextQuestion() {
        deselectAnswer()
        handleNewQuestion()
        if (questionsData.empty()) {
            changeState(QuizFragmentState.LastQuestion)
        }
    }

    private fun handleNewQuestion() {
        val question = questionsData.pop()
        changeState(QuizFragmentState.QuestionLoaded(10 - questionsData.size))
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
    data class FinishedQuiz(val points: Int) : QuizFragmentState()
    data class ShowToast(val message: String) : QuizFragmentState()
    object LastQuestion : QuizFragmentState()
}

enum class Answers {
    A, B, C, D
}

