package com.example.quizapp.presentation.main.registration

import androidx.fragment.app.viewModels
import com.example.quizapp.BR
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentRegistrationBinding
import com.example.quizapp.presentation.base.view.BaseBoundFragment

class RegistrationFragment :
    BaseBoundFragment<FragmentRegistrationBinding, RegistrationViewModel>() {
    override val layoutId: Int = R.layout.fragment_registration
    override val viewModelNameId: Int = BR.viewModel
    override val viewModel: RegistrationViewModel by viewModels()

    override fun bindToViewModel() {
    }

}
