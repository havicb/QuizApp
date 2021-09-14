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
        // todo
    }

    private fun handleSuccessRegistration(registerEntity: RegisterEntity) {
        // todo
    }

    private fun validateFields(): Boolean {
        return validateField(name.value) && validateField(email.value)
                && validateField(username.value) && validateField(password.value)
    }

    private fun validateField(value: String?): Boolean {
        return value!!.isNotEmpty()
    }

    private fun showLoading() {
        _registrationFragmentState.value = RegistrationFragmentState.Loading
    }

    private fun hideLoading() {
        _registrationFragmentState.value = RegistrationFragmentState.NotLoading
    }
}

// According to Bob Uncle: "You should never pass the boolean to function"
// Instead of creating one data class Loading(isLoading: Boolean) and then observing,
// I have extracted it to two separate objects
sealed class RegistrationFragmentState {
    object Init : RegistrationFragmentState()
    object EmptyRegisterField : RegistrationFragmentState()
    object Loading : RegistrationFragmentState()
    object NotLoading : RegistrationFragmentState()
}
