package com.example.quizapp.presentation.main.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quizapp.R
import com.example.quizapp.core.Failure
import com.example.quizapp.data.auth.login.dto.LoginRequest
import com.example.quizapp.data.prefstore.PrefsStore
import com.example.quizapp.domain.auth.login.entity.LoginEntity
import com.example.quizapp.domain.auth.login.usecase.LoginUseCase
import com.example.quizapp.domain.auth.login.usecase.Params
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

    val observeLoginScreenState: StateFlow<LoginFragmentState> get() = _loginFragmentState

    init {
        viewModelScope.launch { getToken() }
    }

    fun loginAsGuest() {
        navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
    }

    fun loginSelected() = viewModelScope.launch {
        showLoading()
        loginUser()
        hideLoading()
    }

    fun registerButtonSelected() {
        _loginFragmentState.value =
            LoginFragmentState.RegisterSelected(R.string.register_fragment_unavailable_error)
    }

    private suspend fun loginUser() {
        loginUseCase(loginParams()).fold(
            ::handleLoginError,
            ::handleSuccessLogin
        )
    }

    private fun loginParams(): Params {
        return Params(LoginRequest(email.value!!, password.value!!))
    }

    private fun handleLoginError(failure: Failure) {
        when (failure) {
            is Failure.NetworkFailure -> handleCommonNetworkErrors(failure)
            else -> observeError.value = Failure.OtherFailure("Dummy error..")
        }
    }

    private fun loginSuccessful() {
        navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
    }

    private fun handleSuccessLogin(loginEntity: LoginEntity) = viewModelScope.launch {
        prefsStore.saveAuthToken(loginEntity.authToken!!)
        loginSuccessful()
    }

    private fun showLoading() {
        _loginFragmentState.value = LoginFragmentState.Loading
    }

    private fun hideLoading() {
        _loginFragmentState.value = LoginFragmentState.NotLoading
    }

    private suspend fun getToken() {
        prefsStore.clear()
        prefsStore.getAuthToken().collect {
            if (it != "") {
                delay(1000)
                loginSuccessful()
            } else {
                _loginFragmentState.value = LoginFragmentState.ShowLogin
            }
        }
    }
}

sealed class LoginFragmentState {
    object Init : LoginFragmentState()
    object ShowLogin : LoginFragmentState()
    object Loading : LoginFragmentState()
    object NotLoading : LoginFragmentState()
    data class RegisterSelected(val message: Int) : LoginFragmentState()
    data class LoginFailed(val message: String) : LoginFragmentState()
}
