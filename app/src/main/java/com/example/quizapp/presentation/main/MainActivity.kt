package com.example.quizapp.presentation.main

import androidx.activity.viewModels
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import com.example.quizapp.BR
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.presentation.common.base.view.BaseBoundActivity
import com.example.quizapp.presentation.common.base.view.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseBoundActivity<MainViewModel, ActivityMainBinding>() {

    override val layoutId = R.layout.activity_main
    override val viewModelNameId = BR.viewModel
    override val viewModel: MainViewModel by viewModels()

    private val navController by lazy { findNavController(R.id.main_graph) }

    override fun onSupportNavigateUp() = navController.navigateUp()
    override fun preInflate() {}
    override fun postInflate(viewDataBinding: ViewDataBinding?) {}
    override fun bindToViewModel() {}
}

class MainViewModel : BaseViewModel()
