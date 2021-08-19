package com.example.quizapp.presentation.main.quiz

import androidx.fragment.app.viewModels
import com.example.quizapp.BR
import com.example.quizapp.R
import com.example.quizapp.core.extensions.showToast
import com.example.quizapp.databinding.FragmentQuizBinding
import com.example.quizapp.presentation.base.view.BaseBoundFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizFragment : BaseBoundFragment<FragmentQuizBinding, QuizViewModel>() {

    override val layoutId = R.layout.fragment_quiz
    override val viewModelNameId = BR.viewModel
    override val viewModel: QuizViewModel by viewModels()

    override fun bindToViewModel() {
        viewModel.quizFragmentState.observe(viewLifecycleOwner) { handleFragmentState(it) }
    }

    fun handleFragmentState(quizFragmentState: QuizFragmentState) = with(binding) {
        when (quizFragmentState) {
            is QuizFragmentState.QuestionSelected -> Unit
            is QuizFragmentState.LastQuestion -> lastQuestion()
        }
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
