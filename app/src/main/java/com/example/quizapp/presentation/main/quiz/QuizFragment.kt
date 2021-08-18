package com.example.quizapp.presentation.main.quiz

import androidx.fragment.app.viewModels
import com.example.quizapp.BR
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentQuizBinding
import com.example.quizapp.presentation.common.base.view.BaseBoundFragment

class QuizFragment : BaseBoundFragment<FragmentQuizBinding, QuizViewModel>() {

    override val layoutId = R.layout.fragment_quiz
    override val viewModelNameId = BR.viewModel
    override val viewModel: QuizViewModel by viewModels()

    override fun bindToViewModel() {

    }
}
