package com.example.quizapp.presentation.main.login

import androidx.fragment.app.viewModels
import com.example.quizapp.BR
import com.example.quizapp.R
import com.example.quizapp.core.extensions.showToast
import com.example.quizapp.databinding.FragmentLoginBinding
import com.example.quizapp.presentation.base.view.BaseBoundFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseBoundFragment<FragmentLoginBinding, LoginViewModel>() {
    override val layoutId = R.layout.fragment_login
    override val viewModelNameId = BR.viewModel
    override val viewModel: LoginViewModel by viewModels()

    override fun bindToViewModel() {
        viewModel.loginResponse.observe(viewLifecycleOwner) { res ->
            showToast(res.toString())
        }
    }
}
