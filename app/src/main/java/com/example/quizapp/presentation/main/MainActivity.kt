package com.example.quizapp.presentation.main

import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.presentation.common.base.view.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutId: Int = R.layout.activity_main
    private val navController by lazy {
        findNavController(R.id.main_graph)
    }

    override fun onSupportNavigateUp() = navController.navigateUp()

    override fun preInflate() {}
    override fun postInflate(viewDataBinding: ViewDataBinding?) {}
}
