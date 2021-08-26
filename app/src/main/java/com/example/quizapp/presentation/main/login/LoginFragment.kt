package com.example.quizapp.presentation.main.login

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.quizapp.BR
import com.example.quizapp.R
import com.example.quizapp.core.extensions.hide
import com.example.quizapp.core.extensions.showToast
import com.example.quizapp.core.extensions.visible
import com.example.quizapp.databinding.FragmentLoginBinding
import com.example.quizapp.presentation.base.view.BaseBoundFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginFragment : BaseBoundFragment<FragmentLoginBinding, LoginViewModel>() {
    override val layoutId = R.layout.fragment_login
    override val viewModelNameId = BR.viewModel
    override val viewModel: LoginViewModel by viewModels()

    override fun bindToViewModel() {
        lifecycleScope.launchWhenResumed {
            viewModel.loginFragmentState.collect {
                handleFragmentState(it)
            }
        }
    }

    private fun handleFragmentState(loginFragmentState: LoginFragmentState) {
        when (loginFragmentState) {
            is LoginFragmentState.Init -> Unit
            is LoginFragmentState.LoginSuccessful -> {
                showToast("Login successful")
                viewModel.loginSuccessful()
            }
            is LoginFragmentState.LoginFailed -> {
                showToast(loginFragmentState.message)
            }
            is LoginFragmentState.Loading -> {
                handleLoading(loginFragmentState.isLoading)
            }
            is LoginFragmentState.ShowToast -> {
                showToast(loginFragmentState.message)
            }
        }
    }

    private fun handleLoading(isLoading: Boolean) = with(binding) {
        if (isLoading)
            loginProgress.visible()
        else
            loginProgress.hide()
    }

    override fun initUI() = with(binding) {
        goToRegister.setOnClickListener {
            viewModel?.registerButtonSelected()
        }
    }
}
