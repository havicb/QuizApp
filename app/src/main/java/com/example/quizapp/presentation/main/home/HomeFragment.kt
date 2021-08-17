package com.example.quizapp.presentation.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quizapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val quizAdapter: QuizHomeAdapter by lazy {
        QuizHomeAdapter() {}
    }
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecycler()
        viewModel.quiz.observe(viewLifecycleOwner) {
            quizAdapter.quizList = it
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initRecycler() = with(binding) {
        quizRecyclerview.apply {
            adapter = quizAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }
}
