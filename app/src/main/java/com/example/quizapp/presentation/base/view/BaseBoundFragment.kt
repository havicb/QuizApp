package com.example.quizapp.presentation.base.view

import androidx.databinding.ViewDataBinding

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
    }

    // method for UI manipulation
    open fun initUI() {}
}
