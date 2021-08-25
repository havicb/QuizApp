package com.example.quizapp.presentation.main.home

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quizapp.BR
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentHomeBinding
import com.example.quizapp.presentation.base.view.BaseBoundFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseBoundFragment<FragmentHomeBinding, HomeViewModel>() {

    override val layoutId = R.layout.fragment_home
    override val viewModelNameId: Int = BR.viewModel
    override val viewModel: HomeViewModel by viewModels()

    private val quizAdapter: QuizHomeAdapter by lazy {
        QuizHomeAdapter(viewModel::quizSelected)
    }

    override fun initUI() {
        initRecycler()
    }

    override fun bindToViewModel() {
        viewModel.quiz.observe(viewLifecycleOwner) {
            quizAdapter.quizList = it
        }
        viewModel.homeFragmentState.observe(viewLifecycleOwner) {
            handleFragmentState(it)
        }
    }

    private fun handleFragmentState(fragmentState: HomeFragmentState) {
        when (fragmentState) {
            is HomeFragmentState.QuizSelected -> SetQuizDialog(
                requireContext(),
                fragmentState.title
            ) { viewModel.startQuiz(it) }
        }
    }

    private fun initRecycler() = with(binding) {
        quizRecyclerview.apply {
            adapter = quizAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }
}
