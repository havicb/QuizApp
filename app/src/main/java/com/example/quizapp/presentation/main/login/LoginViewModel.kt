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
import kotlinx.coroutines.flow.*
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
        loginUseCase.loginUser(LoginRequest(email.value!!, password.value!!))
            .onStart {
                showLoading()
            }.catch { exception ->
                hideLoading()
                showToast(exception.localizedMessage!!)
            }.collect { result ->
                hideLoading()
                when (result) {
                    is BaseResult.Success -> handleSuccessLogin(result)
                    is BaseResult.Error -> handleLoginError(result)
                }
            }
    }

    private fun showToast(localizedMessage: String) {
        _loginFragmentState.value = LoginFragmentState.ShowToast(localizedMessage)
    }

    private fun handleLoginError(errorResponse: BaseResult.Error<ErrorResponse>) {
        _loginFragmentState.value = LoginFragmentState.LoginFailed(errorResponse.response.message)
    }

    private suspend fun handleSuccessLogin(successResponse: BaseResult.Success<LoginEntity>) {
        _loginFragmentState.value = LoginFragmentState.LoginSuccessful
        prefsStore.saveAuthToken(successResponse.data.authToken!!)
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
        prefsStore.getAuthToken().collect {
            if (it != "") {
                _loginFragmentState.value = LoginFragmentState.LoginSuccessful
            }
        }
    }
}

sealed class LoginFragmentState {
    object Init : LoginFragmentState()
    object LoginSuccessful : LoginFragmentState()
    data class Loading(val isLoading: Boolean) : LoginFragmentState()
    data class LoginFailed(val message: String) : LoginFragmentState()
    data class ShowToast(val message: String) : LoginFragmentState()
}
