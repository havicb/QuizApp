package com.example.quizapp.presentation.base.view

import androidx.lifecycle.ViewModel

// Represent a view that can be bound to VM
interface BoundView<ViewModelType : ViewModel> {

    // represents variable that will be bound to xml layout for data binding
    val viewModelNameId: Int

    val viewModel: ViewModelType

    // invoked when view is created and bound to VM
    fun bindToViewModel()
}
