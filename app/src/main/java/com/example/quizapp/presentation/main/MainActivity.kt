package com.example.quizapp.presentation.main

import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import com.example.quizapp.BR
import com.example.quizapp.R
import com.example.quizapp.core.extensions.showScreen
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.presentation.base.view.BaseBoundActivity
import com.example.quizapp.presentation.base.view.BaseViewModel
import com.example.quizapp.presentation.base.view.MainScreenState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseBoundActivity<MainViewModel, ActivityMainBinding>() {

    override val layoutId = R.layout.activity_main
    override val viewModelNameId = BR.viewModel
    override val viewModel: MainViewModel by viewModels()

    private val navController by lazy { findNavController(R.id.main_navigation_host) }

    override fun onSupportNavigateUp() = navController.navigateUp()
    override fun preInflate() {}
    override fun postInflate(viewDataBinding: ViewDataBinding?) {
        initUI()
        bindToViewModel()
    }

    override fun bindToViewModel() {
        // todo for some reason live data not loading observer here !?
    }

    fun updateScreen(screenState: MainScreenState) = with(binding) {
        when (screenState) {
            is MainScreenState.NoInternetConnectionScreen -> {
                viewFlipper.showScreen(noInternetConnectionScreen.root)
                onButtonClick(noInternetConnectionScreen.retryConnect) {
                    reloadScreen {
                        screenState.onRetryButtonSelected!!.invoke()
                    }
                }
            }
        }
    }

    private fun reloadScreen(onReload: () -> Unit) = with(binding) {
        viewFlipper.showScreen(mainNavigationHost)
        onReload()
    }

    private fun onButtonClick(button: Button, listener: () -> Unit) {
        button.setOnClickListener { listener.invoke() }
    }

    override fun initUI() {
        setSupportActionBar(binding.mainToolbar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        super.initUI()
    }
}

class MainViewModel() : BaseViewModel() {}
