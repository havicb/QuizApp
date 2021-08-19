package com.example.quizapp.presentation.main.quiz

import android.view.View
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quizapp.R
import com.example.quizapp.domain.questions.entity.QuestionEntity
import com.example.quizapp.domain.questions.usecase.GetQuestionsUseCase
import com.example.quizapp.presentation.base.view.BaseViewModel
import com.example.quizapp.presentation.main.quiz.question.Selectable
import com.example.quizapp.presentation.main.quiz.question.toView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val questionsUseCase: GetQuestionsUseCase
) : BaseViewModel() {

    private val questionsData = Stack<QuestionEntity>()
    private var currentQuestion: QuestionEntity? = null

    private val _quizFragmentState = MutableLiveData<QuizFragmentState>()

    val question = MutableLiveData<String>()
    val answerA = MutableLiveData<String>()
    val answerB = MutableLiveData<String>()
    val answerC = MutableLiveData<String>()
    val answerD = MutableLiveData<String>()

    val quizFragmentState: LiveData<QuizFragmentState> get() = _quizFragmentState

    init {
        fetch()
    }

    fun checkQuestion(userAnswer: String) = viewModelScope.launch {
        if (userAnswer == currentQuestion?.correctAnswer) {
            changeState(QuizFragmentState.CorrectAnswer())
            delay(3000)
            nextQuestion()
        } else {
            changeState(QuizFragmentState.InCorrectAnswer())
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

    private fun fetch() = viewModelScope.launch {
        val data = questionsUseCase.fetchQuestionData(10, 10, "easy")
        for (i in (data.questions.size - 1) downTo 0) {
            questionsData.add(data.questions[i])
        }
        changeState(QuizFragmentState.QuestionsLoaded)
        handleNewQuestion(questionsData.pop())
    }

    private fun handleNewQuestion(nextQuestion: QuestionEntity) {
        currentQuestion = nextQuestion
        val questionView = nextQuestion.toView()
        question.value = questionView.title
        answerA.value = questionView.answerA
        answerB.value = questionView.answerB
        answerC.value = questionView.answerC
        answerD.value = questionView.answerD
    }
}

sealed class QuizFragmentState {
    object QuestionsLoaded : QuizFragmentState()
    object AnswerUnSelected : QuizFragmentState()
    data class AnswerSelected(val selectable: Selectable) : QuizFragmentState()
    data class CorrectAnswer(val color: Int = R.color.lightGreen) : QuizFragmentState()
    data class InCorrectAnswer(val color: Int = R.color.red) : QuizFragmentState()
    object LastQuestion : QuizFragmentState()
}

data class SelectedAnswerModelUI(val answer: TextView) : Selectable {
    override val textView = answer
    override val backgroundColor = R.color.yellow
}
