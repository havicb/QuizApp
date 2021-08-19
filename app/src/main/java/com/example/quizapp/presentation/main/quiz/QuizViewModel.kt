package com.example.quizapp.presentation.main.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quizapp.domain.questions.entity.QuestionEntity
import com.example.quizapp.domain.questions.usecase.GetQuestionsUseCase
import com.example.quizapp.presentation.base.view.BaseViewModel
import com.example.quizapp.presentation.main.quiz.question.QuestionView
import com.example.quizapp.presentation.main.quiz.question.toView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val questionsUseCase: GetQuestionsUseCase
) : BaseViewModel() {

    private val questionsData = Stack<QuestionEntity>()

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

    fun fetch() = viewModelScope.launch {
        val data = questionsUseCase.fetchQuestionData(10, 10, "easy")
        for (i in (data.questions.size - 1) downTo 0) {
            questionsData.add(data.questions[i])
        }
        handleNewQuestion(questionsData.pop().toView())
    }

    fun nextQuestion() {
        handleNewQuestion(questionsData.pop().toView())
        if (questionsData.empty()) {
            _quizFragmentState.value = QuizFragmentState.LastQuestion
        }
    }

    fun onLastQuestion() {
    }

    private fun handleNewQuestion(questionView: QuestionView) {
        question.value = questionView.title
        answerA.value = questionView.answerA
        answerB.value = questionView.answerB
        answerC.value = questionView.answerC
        answerD.value = questionView.answerD
    }
}

sealed class QuizFragmentState {
    object QuestionSelected : QuizFragmentState()
    object LastQuestion : QuizFragmentState()
}
