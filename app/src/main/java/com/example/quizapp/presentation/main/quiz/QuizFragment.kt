package com.example.quizapp.presentation.main.quiz

import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.quizapp.BR
import com.example.quizapp.R
import com.example.quizapp.core.extensions.*
import com.example.quizapp.databinding.FragmentQuizBinding
import com.example.quizapp.presentation.base.view.BaseBoundFragment
import com.example.quizapp.presentation.main.quiz.question.Selectable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizFragment : BaseBoundFragment<FragmentQuizBinding, QuizViewModel>() {

    private var lastSelectedAnswer: TextView? = null

    override val layoutId = R.layout.fragment_quiz
    override val viewModelNameId = BR.viewModel
    override val viewModel: QuizViewModel by viewModels()

    override fun bindToViewModel() {
        viewModel.quizFragmentState.observe(viewLifecycleOwner) { handleFragmentState(it) }
    }

    private fun handleFragmentState(quizFragmentState: QuizFragmentState) {
        when (quizFragmentState) {
            is QuizFragmentState.QuestionLoaded -> onQuestionLoaded(quizFragmentState.questionNumber)
            is QuizFragmentState.AnswerSelected -> handleAnswerSelection(
                quizFragmentState.selectable.textView!!,
                quizFragmentState.selectable
            )
            is QuizFragmentState.AnswerUnSelected -> resetAnswer()
            is QuizFragmentState.LastQuestion -> lastQuestion()
            is QuizFragmentState.InCorrectAnswer -> {
                handleSubmittedAnswer(quizFragmentState.color)
            }
            is QuizFragmentState.CorrectAnswer -> handleSubmittedAnswer(quizFragmentState.color)
            is QuizFragmentState.FinishedQuiz -> {
                handleFinishedQuiz(quizFragmentState.points)
            }
        }
    }

    private fun handleFinishedQuiz(points: Int) {
        showGenericDialog(
            getString(R.string.end_quiz_title),
            getString(R.string.end_quiz_message, points)
        ) {
            hideViews()
            findNavController().navigate(R.id.action_quizFragment_to_homeFragment)
        }
    }

    private fun handleAnswerSelection(selectedAnswer: TextView, selectable: Selectable) {
        selectedAnswer.setBackgroundColor(getColor(selectable.backgroundColor))
        selectedAnswer.setTextColor(getColor(selectable.textColor))
        lastSelectedAnswer = selectedAnswer
        binding.setVariable(BR.selectedAnswer, lastSelectedAnswer!!.text.toString())
        binding.btnProceedToNextQuestion.enable()
    }

    private fun handleSubmittedAnswer(backgroundColor: Int) = with(binding) {
        lastSelectedAnswer!!.setBackgroundColor(getColor(backgroundColor))
        answerA.disable()
        answerB.disable()
        answerC.disable()
        answerD.disable()
        btnProceedToNextQuestion.disable()
    }

    private fun resetAnswer() {
        lastSelectedAnswer?.setBackgroundColor(getColor(R.color.white))
    }

    private fun onQuestionLoaded(questionNumber: Int) = with(binding) {
        questionLoadingProgress.hide()
        quizProgress.progress = questionNumber
        showViews()
        answerA.enable()
        answerB.enable()
        answerC.enable()
        answerD.enable()
        btnProceedToNextQuestion.disable()
    }

    private fun hideViews() = with(binding) {
        question.hide()
        questionNumber.hide()
        firstTwoAnswersLl.hide()
        lastTwoAnswersLl.hide()
        btnProceedToNextQuestion.hide()
    }

    private fun showViews() = with(binding) {
        questionNumber.visible()
        question.visible()
        firstTwoAnswersLl.visible()
        lastTwoAnswersLl.visible()
        btnProceedToNextQuestion.visible()
    }

    private fun lastQuestion() = with(binding) {
        btnProceedToNextQuestion.text = getString(R.string.btn_finish_quiz_text)
        btnProceedToNextQuestion.setOnClickListener { viewModel?.onLastQuestion() }
    }
}
