package com.example.quizapp.presentation.main.quiz

import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.quizapp.BR
import com.example.quizapp.R
import com.example.quizapp.core.extensions.getColor
import com.example.quizapp.core.extensions.hide
import com.example.quizapp.core.extensions.showToast
import com.example.quizapp.core.extensions.visible
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
            is QuizFragmentState.QuestionsLoaded -> onQuestionsLoaded()
            is QuizFragmentState.AnswerSelected -> handleAnswerSelection(
                quizFragmentState.selectable.textView!!,
                quizFragmentState.selectable
            )
            is QuizFragmentState.AnswerUnSelected -> resetAnswer()
            is QuizFragmentState.LastQuestion -> lastQuestion()
            is QuizFragmentState.InCorrectAnswer -> handleSubmittedAnswer(quizFragmentState.color)
            is QuizFragmentState.CorrectAnswer -> handleSubmittedAnswer(quizFragmentState.color)
        }
    }

    private fun handleAnswerSelection(selectedAnswer: TextView, selectable: Selectable) {
        selectedAnswer.setBackgroundColor(getColor(selectable.backgroundColor))
        selectedAnswer.setTextColor(getColor(selectable.textColor))
        lastSelectedAnswer = selectedAnswer
        binding.setVariable(BR.selectedAnswer, lastSelectedAnswer!!.text.toString())
        binding.btnProceedToNextQuestion.isEnabled = true
    }

    private fun handleSubmittedAnswer(color: Int) {
        lastSelectedAnswer!!.setBackgroundColor(getColor(color))
    }

    private fun resetAnswer() {
        lastSelectedAnswer?.setBackgroundColor(getColor(R.color.white))
    }

    private fun onQuestionsLoaded() = with(binding) {
        questionLoadingProgress.hide()
        questionNumber.visible()
        question.visible()
        firstTwoAnswersLl.visible()
        lastTwoAnswersLl.visible()
        btnProceedToNextQuestion.visible()
        btnProceedToNextQuestion.isEnabled = false
    }

    private fun lastQuestion() = with(binding) {
        btnProceedToNextQuestion.text = "Finish quiz"
        btnProceedToNextQuestion.setOnClickListener {
            viewModel?.onLastQuestion()
            requireContext().showToast("Last question settings..")
        }
    }
}

// val argument = requireArguments().getSerializable("ARG") as QuizSettings
//        requireContext().showToast("Argument -> $argument")
