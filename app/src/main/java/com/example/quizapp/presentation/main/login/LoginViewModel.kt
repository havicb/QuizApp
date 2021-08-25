package com.example.quizapp.presentation.main.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.auth.login.remote.dto.LoginRequest
import com.example.quizapp.data.auth.login.remote.dto.LoginResponse
import com.example.quizapp.data.prefstore.PrefsStore
import com.example.quizapp.domain.auth.login.usecase.LoginUseCase
import com.example.quizapp.presentation.base.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val prefsStore: PrefsStore
) : BaseViewModel() {

    private var _loginFragmentState = MutableStateFlow<LoginFragmentState>(LoginFragmentState.Init)
    private var _loginResponse = MutableLiveData<LoginResponse>()

    val email = MutableLiveData("")
    val password = MutableLiveData("")

    val loginFragmentState: StateFlow<LoginFragmentState> get() = _loginFragmentState

    init {
        viewModelScope.launch {
            prefsStore.getAuthToken().collect {
                if (it != "") {
                    _loginFragmentState.value = LoginFragmentState.LoginSuccessful
                }
            }
        }
    }

    fun loginSuccessful() {
        navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
    }

    fun loginUser() = viewModelScope.launch {
        val response = loginUseCase.loginUser(LoginRequest(email.value!!, password.value!!))
        if (response.success) {
            _loginFragmentState.value = LoginFragmentState.LoginSuccessful
            prefsStore.saveAuthToken(response.authToken!!)
        } else {
            _loginFragmentState.value = LoginFragmentState.LoginFailed
        }
    }

    fun registerButtonSelected() {
        navigate(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment())
    }
}

sealed class LoginFragmentState {
    object Init : LoginFragmentState()
    object LoginSuccessful : LoginFragmentState()
    object LoginFailed : LoginFragmentState()
}
