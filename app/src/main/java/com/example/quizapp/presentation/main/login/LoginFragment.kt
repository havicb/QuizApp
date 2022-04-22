package com.example.quizapp.presentation.main.login

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.quizapp.BR
import com.example.quizapp.R
import com.example.quizapp.core.Failure
import com.example.quizapp.core.extensions.hide
import com.example.quizapp.core.extensions.showToast
import com.example.quizapp.core.extensions.show
import com.example.quizapp.databinding.FragmentLoginBinding
import com.example.quizapp.presentation.base.view.BaseBoundFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginFragment : BaseBoundFragment<FragmentLoginBinding, LoginViewModel>() {
    override val layoutId = R.layout.fragment_login
    override val viewModelNameId = BR.viewModel
    override val viewModel: LoginViewModel by viewModels()

    override fun bindToViewModel() = with(viewModel) {
        lifecycleScope.launchWhenResumed {
            observeLoginScreenState.collect {
                handleFragmentState(it)
            }
        }
        observeError.observe(viewLifecycleOwner) {
            handleFailure(it)
        }
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.NetworkFailure -> handleCommonNetworkErrors(failure)
            else -> showToast(getString(R.string.other_error))
        }
    }

    private fun handleFragmentState(loginFragmentState: LoginFragmentState) {
        when (loginFragmentState) {
            is LoginFragmentState.Init -> Unit
            is LoginFragmentState.LoginFailed -> showToast(loginFragmentState.message)
            is LoginFragmentState.Loading -> onLoading()
            is LoginFragmentState.NotLoading -> onLoadingStopped()
            is LoginFragmentState.ShowLogin -> showLogin()
            is LoginFragmentState.RegisterSelected -> showToast(getString(loginFragmentState.message))
        }
    }

    private fun showLogin() = with(binding) {
        loginLoading.hide()
        loginViews.show()
    }

    private fun onLoading() = binding.loginProgress.show()
    private fun onLoadingStopped() = binding.loginProgress.hide()
}
