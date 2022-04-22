package com.example.quizapp.presentation.main.quiz

import androidx.fragment.app.viewModels
import com.example.quizapp.BR
import com.example.quizapp.R
import com.example.quizapp.core.extensions.*
import com.example.quizapp.databinding.FragmentQuizBinding
import com.example.quizapp.presentation.base.view.BaseBoundFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizFragment : BaseBoundFragment<FragmentQuizBinding, QuizViewModel>() {

    override val layoutId = R.layout.fragment_quiz
    override val viewModelNameId = BR.viewModel
    override val viewModel: QuizViewModel by viewModels()

    override fun bindToViewModel() = with(viewModel) {
        observeQuizScreenState.observe(viewLifecycleOwner) { handleFragmentState(it) }
        observeFirstAnswerState.observe(viewLifecycleOwner) { binding.answerA.state = it }
        observeSecondAnswerState.observe(viewLifecycleOwner) { binding.answerB.state = it }
        observeThirdAnswerState.observe(viewLifecycleOwner) { binding.answerC.state = it }
        observeFourthAnswerState.observe(viewLifecycleOwner) { binding.answerD.state = it }
        observeButtonSelected.observe(viewLifecycleOwner) { handleSubmittedAnswer() }
    }

    private fun handleFragmentState(quizFragmentState: QuizFragmentState) {
        when (quizFragmentState) {
            is QuizFragmentState.QuestionLoaded -> onQuestionLoaded(quizFragmentState.questionNumber)
            is QuizFragmentState.FinishedQuiz -> handleFinishedQuiz(quizFragmentState.points)
            is QuizFragmentState.ShowToast -> showToast(quizFragmentState.message)
            is QuizFragmentState.LastQuestion -> lastQuestion()
        }
    }

    private fun handleFinishedQuiz(points: Int) {
        showGenericDialog(
            getString(R.string.end_quiz_title),
            getString(R.string.end_quiz_message, points)
        ) {
            hideViews()
            viewModel.onQuizFinished()
        }
    }

    private fun handleSubmittedAnswer() = with(binding) {
        answerA.disable()
        answerB.disable()
        answerC.disable()
        answerD.disable()
    }

    private fun onQuestionLoaded(questionNumber: Int) = with(binding) {
        questionLoadingProgress.hide()
        quizProgress.progress = questionNumber
        answerA.enable()
        answerB.enable()
        answerC.enable()
        answerD.enable()
        showViews()
    }

    private fun hideViews() = with(binding) {
        questionGroup.hide()
        answerGroup.hide()
        btnProceedToNextQuestion.hide()
    }

    private fun showViews() = with(binding) {
        questionGroup.show()
        answerGroup.show()
        btnProceedToNextQuestion.show()
    }

    private fun lastQuestion() = with(binding) {
        btnProceedToNextQuestion.text = getString(R.string.btn_finish_quiz_text)
        btnProceedToNextQuestion.setOnClickListener { viewModel?.onLastQuestion() }
    }
}
