package com.example.quizapp.presentation.base

import androidx.navigation.NavDirections

sealed class NavigationEvent {
    data class To(val directions: NavDirections) : NavigationEvent()
    object Back : NavigationEvent()
}
