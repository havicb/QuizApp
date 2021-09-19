package com.example.quizapp.presentation.main.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quizapp.core.Failure
import com.example.quizapp.data.prefstore.PrefsStore
import com.example.quizapp.domain.auth.login.entity.LoginEntity
import com.example.quizapp.domain.auth.login.usecase.LoginUseCase
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

    // My api is down, I can not send http request anymore
    // TODO i will update BE
    fun loginUser() = viewModelScope.launch {
        showLoading()
        delay(2000)
        /*loginUseCase(Params(LoginRequest(email.value!!, password.value!!))).fold(
            ::handleLoginError,
            ::handleSuccessLogin
        )*/
        handleSuccessLogin(LoginEntity("jwt_token", "Success", true))
        hideLoading()
    }

    fun registerButtonSelected() {
        _loginFragmentState.value =
            LoginFragmentState.RegisterSelected("Register fragment is currently unavailable!")
        //navigate(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment())
    }

    private fun handleLoginError(failure: Failure) {
        when (failure) {
            is Failure.NetworkFailure -> handleCommonNetworkErrors(failure)
            else -> error.value = Failure.OtherFailure("Dummy error..")
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

// According to Bob Uncle: "You should never pass the boolean to function"
// Instead of creating one data class Loading(isLoading: Boolean) and then observing,
// I have extracted it to two separate objects
sealed class LoginFragmentState {
    object Init : LoginFragmentState()
    object ShowLogin : LoginFragmentState()
    object Loading : LoginFragmentState()
    object NotLoading : LoginFragmentState()
    data class RegisterSelected(val message: String) : LoginFragmentState()
    data class LoginFailed(val message: String) : LoginFragmentState()
}
