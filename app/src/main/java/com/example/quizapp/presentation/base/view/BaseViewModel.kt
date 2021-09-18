package com.example.quizapp.presentation.base.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.example.quizapp.core.Failure
import com.example.quizapp.presentation.base.NavigationEvent
import com.example.quizapp.presentation.base.SingleLiveEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    private val _screenState =  MutableLiveData<MainScreenState>()

    val error: MutableLiveData<Failure> = MutableLiveData()
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
            is Failure.ServerFailure -> error.value = Failure.ServerFailure
            is Failure.NetworkConnectionFailure -> {
                error.value = Failure.NetworkConnectionFailure(failure.message)
                delay(1000)
                _screenState.value = MainScreenState.NoInternetConnectionScreen(onRetry)
            }
            is Failure.BadRequest -> error.value = Failure.BadRequest(failure.message)
            is Failure.Forbidden -> error.value = Failure.Forbidden
            is Failure.NotAuthorized -> error.value = Failure.NotAuthorized
            is Failure.NotFound -> error.value = Failure.NotFound
        }
    }
}

sealed class MainScreenState() {
    data class NoInternetConnectionScreen(val onRetryButtonSelected: (() -> Unit)? = null) : MainScreenState()
}

