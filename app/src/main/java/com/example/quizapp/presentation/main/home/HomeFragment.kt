package com.example.quizapp.presentation.main.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentHomeBinding
import com.example.quizapp.presentation.common.base.view.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val layoutId = R.layout.fragment_home
    private val quizAdapter: QuizHomeAdapter by lazy { QuizHomeAdapter() {} }

    private fun initRecycler() = with(binding) {
        quizRecyclerview.apply {
            adapter = quizAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    override fun preInflate() {
    }

    override fun postInflate(viewBindingType: ViewDataBinding?) {
    }
}
