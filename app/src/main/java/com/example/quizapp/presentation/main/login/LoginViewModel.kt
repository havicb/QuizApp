package com.example.quizapp.presentation.main.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.auth.login.remote.dto.LoginRequest
import com.example.quizapp.data.auth.login.remote.dto.LoginResponse
import com.example.quizapp.domain.auth.login.usecase.LoginUseCase
import com.example.quizapp.presentation.base.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    private var _loginResponse = MutableLiveData<LoginResponse>()

    val email = MutableLiveData("")
    val password = MutableLiveData("")

    val loginResponse: LiveData<LoginResponse> get() = _loginResponse

    fun loginUser() = viewModelScope.launch {
        _loginResponse.value = loginUseCase.loginUser(LoginRequest(email.value!!, password.value!!))
    }
}
