package com.example.quizapp.presentation.base.view

import androidx.databinding.ViewDataBinding
import com.example.quizapp.core.extensions.navController
import com.example.quizapp.presentation.base.NavigationEvent

abstract class BaseBoundFragment<ViewBindingType : ViewDataBinding, ViewModelType : BaseViewModel> :
    BaseFragment<ViewBindingType>(), BoundView<ViewModelType> {

    override fun preInflate() {
    }

    override fun postInflate(viewBindingType: ViewDataBinding?) {
        viewBindingType?.let {
            it.lifecycleOwner = this
            it.setVariable(viewModelNameId, viewModel)
            it.executePendingBindings()
        }
        initUI()
        bindToViewModel()
        initNavigationObserver()
    }

    // method for UI manipulation
    open fun initUI() {}

    protected fun initNavigationObserver() {
        viewModel.navigationEvent.observe(viewLifecycleOwner) {
            when (it) {
                is NavigationEvent.To -> navController.navigate(it.directions)
                is NavigationEvent.Back -> navController.navigateUp()
            }
        }
    }
}
