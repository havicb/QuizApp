package com.example.quizapp.presentation.main.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quizapp.core.Either
import com.example.quizapp.core.Failure
import com.example.quizapp.data.auth.register.dto.RegisterRequest
import com.example.quizapp.domain.auth.register.entity.RegisterEntity
import com.example.quizapp.domain.auth.register.usecase.Params
import com.example.quizapp.domain.auth.register.usecase.RegisterUserUseCase
import com.example.quizapp.presentation.base.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : BaseViewModel() {

    private var _registrationFragmentState =
        MutableLiveData<RegistrationFragmentState>(RegistrationFragmentState.Init)

    var name = MutableLiveData("")
    var email = MutableLiveData("")
    var username = MutableLiveData("")
    var password = MutableLiveData("")

    val registrationFragmentState: LiveData<RegistrationFragmentState> get() = _registrationFragmentState

    fun register() = viewModelScope.launch {
        if (validateFields()) {
            showLoading()
            registerUserUseCase(
                Params(
                    RegisterRequest(
                        name.value!!,
                        email.value!!,
                        username.value!!,
                        password.value!!
                    )
                )
            ).fold(::handleFailure, ::handleSuccessRegistration)
            hideLoading()
        } else {
            _registrationFragmentState.value = RegistrationFragmentState.EmptyRegisterField
        }
    }

    private fun handleFailure(failure: Failure) {

    }

    private fun handleSuccessRegistration(registerEntity: RegisterEntity) {

    }

    private fun validateFields(): Boolean {
        return name.value != "" && email.value != "" && username.value != "" && password.value != ""
    }

    private fun showLoading() {
        _registrationFragmentState.value = RegistrationFragmentState.Loading(true)
    }

    private fun hideLoading() {
        _registrationFragmentState.value = RegistrationFragmentState.Loading(false)
    }

    private fun showToast(message: String) {
        _registrationFragmentState.value = RegistrationFragmentState.ShowToast(message)
    }
}

sealed class RegistrationFragmentState {
    object Init : RegistrationFragmentState()
    object EmptyRegisterField : RegistrationFragmentState()
    data class Loading(val isLoading: Boolean) : RegistrationFragmentState()
    data class ShowToast(val message: String) : RegistrationFragmentState()
}
