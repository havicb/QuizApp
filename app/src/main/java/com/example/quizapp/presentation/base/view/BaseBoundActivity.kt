package com.example.quizapp.presentation.base.view

import androidx.databinding.ViewDataBinding

abstract class BaseBoundActivity<ViewModelType : BaseViewModel, ViewDataBindingType : ViewDataBinding> :
    BaseActivity<ViewDataBindingType>(), BoundView<ViewModelType> {

    override fun preInflate() {
    }

    override fun postInflate(viewDataBinding: ViewDataBinding?) {
        viewDataBinding?.let {
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
