package com.example.quizapp.presentation.base.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.quizapp.core.Failure
import com.example.quizapp.presentation.base.NavigationEvent
import com.example.quizapp.presentation.base.SingleLiveEvent

open class BaseViewModel : ViewModel() {

    val error: MutableLiveData<Failure> = MutableLiveData()
    val navigationEvent: SingleLiveEvent<NavigationEvent> = SingleLiveEvent()

    protected fun navigate(navAction: NavigationEvent) {
        navigationEvent.value = navAction
    }

    protected fun navigate(navDirections: NavDirections) {
        navigationEvent.value = NavigationEvent.To(navDirections)
    }

    protected fun handleCommonNetworkErrors(failure: Failure.NetworkFailure) {
        when (failure) {
            is Failure.ServerFailure -> error.value = Failure.ServerFailure
            is Failure.NetworkConnectionFailure -> error.value =
                Failure.NetworkConnectionFailure(failure.message)
            is Failure.BadRequest -> error.value = Failure.BadRequest(failure.message)
            is Failure.Forbidden -> error.value = Failure.Forbidden
            is Failure.NotAuthorized -> error.value = Failure.NotAuthorized
            is Failure.NotFound -> error.value = Failure.NotFound
        }
    }
}
