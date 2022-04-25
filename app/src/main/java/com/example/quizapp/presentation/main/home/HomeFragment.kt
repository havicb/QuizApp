package com.example.quizapp.presentation.main.home

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quizapp.BR
import com.example.quizapp.R
import com.example.quizapp.core.extensions.onActionDone
import com.example.quizapp.databinding.FragmentHomeBinding
import com.example.quizapp.presentation.base.view.BaseBoundFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseBoundFragment<FragmentHomeBinding, HomeViewModel>() {

    override val layoutId = R.layout.fragment_home
    override val viewModelNameId: Int = BR.viewModel
    override val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var quizAdapter: QuizHomeAdapter

    override fun initUI() {
        initRecycler()
    }

    override fun bindToViewModel() = with(viewModel) {
        binding.searchView.onActionDone {
            viewModel.onSearchButton()
            true
        }
        observeUserPoints.observe(viewLifecycleOwner) { binding.textViewPoints.text = it.toString() }
        observeQuizzes.observe(viewLifecycleOwner) { quizAdapter.updateQuizzes(it) }
        observeSelectedQuiz.observe(viewLifecycleOwner) { onQuizSelected(it) }
    }

    private fun onQuizSelected(title: String) {
        SetQuizDialog(
            requireContext(),
            title
        ) { viewModel.startQuiz(it) }
    }

    private fun initRecycler() = with(binding) {
        quizRecyclerview.apply {
            adapter = quizAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
        quizAdapter.onQuizSelected = viewModel!!::quizSelected
    }
}
