package com.example.quizapp.presentation.base.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.example.quizapp.core.Failure
import com.example.quizapp.presentation.base.NavigationEvent
import com.example.quizapp.presentation.base.SingleLiveEvent
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    private val _screenState = MutableLiveData<MainScreenState>()

    val observeError: MutableLiveData<Failure> = MutableLiveData()
    val navigationEvent: SingleLiveEvent<NavigationEvent> = SingleLiveEvent()
    val screenState: LiveData<MainScreenState> get() = _screenState

    protected fun navigate(navAction: NavigationEvent) {
        navigationEvent.value = navAction
    }

    protected fun navigate(navDirections: NavDirections) {
        navigationEvent.value = NavigationEvent.To(navDirections)
    }

    protected fun handleCommonNetworkErrors(failure: Failure.NetworkFailure, onRetry: (() -> Unit)? = null) = viewModelScope.launch {
        when (failure) {
            is Failure.ServerFailure -> observeError.value = Failure.ServerFailure(failure.message)
            is Failure.NetworkConnectionFailure -> {
                _screenState.value = MainScreenState.NoInternetConnectionScreen(onRetry)
                observeError.value = Failure.NetworkConnectionFailure(failure.message)
            }
            is Failure.BadRequest -> observeError.value = Failure.BadRequest(failure.message)
            is Failure.Forbidden -> observeError.value = Failure.Forbidden(failure.message)
            is Failure.NotAuthorized -> observeError.value = Failure.NotAuthorized(failure.message)
            is Failure.NotFound -> observeError.value = Failure.NotFound(failure.message)
        }
    }
}

sealed class MainScreenState() {
    data class NoInternetConnectionScreen(val retryButtonSelected: (() -> Unit)? = null) : MainScreenState()
}

