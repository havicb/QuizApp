package com.example.quizapp.presentation.base.view

import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.quizapp.presentation.base.NavigationEvent
import com.example.quizapp.presentation.base.SingleLiveEvent

open class BaseViewModel : ViewModel() {

    val navigationEvent: SingleLiveEvent<NavigationEvent> = SingleLiveEvent()

    protected fun navigate(navAction: NavigationEvent) {
        navigationEvent.value = navAction
    }

    protected fun navigate(navDirections: NavDirections) {
        navigationEvent.value = NavigationEvent.To(navDirections)
    }
}
