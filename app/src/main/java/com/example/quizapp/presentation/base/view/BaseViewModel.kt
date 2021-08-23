package com.example.quizapp.presentation.base.view

import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.quizapp.prefstore.PrefsStore
import com.example.quizapp.presentation.base.NavigationEvent
import com.example.quizapp.presentation.base.SingleLiveEvent
import javax.inject.Inject

open class BaseViewModel : ViewModel() {

    @Inject lateinit var prefsStore: PrefsStore
    val navigationEvent: SingleLiveEvent<NavigationEvent> = SingleLiveEvent()

    protected fun navigate(navAction: NavigationEvent) {
        navigationEvent.value = navAction
    }

    protected fun navigate(navDirections: NavDirections) {
        navigationEvent.value = NavigationEvent.To(navDirections)
    }
}
