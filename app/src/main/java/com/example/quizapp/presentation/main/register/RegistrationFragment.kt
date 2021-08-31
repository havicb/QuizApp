package com.example.quizapp.presentation.main.register

import androidx.fragment.app.viewModels
import com.example.quizapp.BR
import com.example.quizapp.R
import com.example.quizapp.core.extensions.hide
import com.example.quizapp.core.extensions.showSnackbar
import com.example.quizapp.core.extensions.showToast
import com.example.quizapp.core.extensions.visible
import com.example.quizapp.databinding.FragmentRegistrationBinding
import com.example.quizapp.presentation.base.view.BaseBoundFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment :
    BaseBoundFragment<FragmentRegistrationBinding, RegistrationViewModel>() {
    override val layoutId: Int = R.layout.fragment_registration
    override val viewModelNameId: Int = BR.viewModel
    override val viewModel: RegistrationViewModel by viewModels()

    override fun bindToViewModel() {
        viewModel.registrationFragmentState.observe(viewLifecycleOwner) {
            handleFragmentState(it)
        }
    }

    private fun handleFragmentState(state: RegistrationFragmentState) {
        when (state) {
            is RegistrationFragmentState.Init -> Unit
            is RegistrationFragmentState.Loading -> {
                handleLoading(state.isLoading)
            }
            is RegistrationFragmentState.ShowToast -> showToast(state.message)
            is RegistrationFragmentState.EmptyRegisterField -> { showSnackbar("No empty fields are allowed!") }
        }
    }

    private fun handleLoading(isLoading: Boolean) = with(binding) {
        if (isLoading)
            registrationProgress.visible()
        else
            registrationProgress.hide()
    }
}
