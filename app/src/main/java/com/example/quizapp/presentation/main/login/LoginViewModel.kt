package com.example.quizapp.presentation.main.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.ErrorResponse
import com.example.quizapp.data.auth.login.dto.LoginRequest
import com.example.quizapp.data.prefstore.PrefsStore
import com.example.quizapp.domain.auth.login.entity.LoginEntity
import com.example.quizapp.domain.auth.login.usecase.LoginUseCase
import com.example.quizapp.domain.common.BaseResult
import com.example.quizapp.presentation.base.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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

    val email = MutableLiveData("")
    val password = MutableLiveData("")

    val loginFragmentState: StateFlow<LoginFragmentState> get() = _loginFragmentState

    init {
        viewModelScope.launch { getToken() }
    }

    fun loginSuccessful() {
        navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
    }

    fun loginUser() = viewModelScope.launch {
        showLoading()
        when (val call = loginUseCase.loginUser(LoginRequest(email.value!!, password.value!!))) {
            is BaseResult.Success -> handleSuccessLogin(call.data)
            is BaseResult.Error -> handleLoginError(call.response)
        }
        hideLoading()
    }

    private fun showToast(localizedMessage: String) {
        _loginFragmentState.value = LoginFragmentState.ShowToast(localizedMessage)
    }

    private fun handleLoginError(errorResponse: ErrorResponse) {
        _loginFragmentState.value = LoginFragmentState.LoginFailed(errorResponse.message)
    }

    private suspend fun handleSuccessLogin(successResponse: LoginEntity) {
        _loginFragmentState.value = LoginFragmentState.LoginSuccessful
        prefsStore.saveAuthToken(successResponse.authToken!!)
    }

    private fun showLoading() {
        _loginFragmentState.value = LoginFragmentState.Loading(true)
    }

    private fun hideLoading() {
        _loginFragmentState.value = LoginFragmentState.Loading(false)
    }

    fun registerButtonSelected() {
        navigate(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment())
    }

    private suspend fun getToken() {
        prefsStore.clear()
        prefsStore.getAuthToken().collect {
            if (it != "") {
                delay(1000)
                _loginFragmentState.value = LoginFragmentState.LoginSuccessful
            } else {
                _loginFragmentState.value = LoginFragmentState.ShowLogin
            }
        }
    }
}

sealed class LoginFragmentState {
    object Init : LoginFragmentState()
    object ShowLogin : LoginFragmentState()
    object LoginSuccessful : LoginFragmentState()
    data class Loading(val isLoading: Boolean) : LoginFragmentState()
    data class LoginFailed(val message: String) : LoginFragmentState()
    data class ShowToast(val message: String) : LoginFragmentState()
}
