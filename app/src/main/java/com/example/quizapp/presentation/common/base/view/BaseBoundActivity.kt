package com.example.quizapp.presentation.common.base.view

import androidx.databinding.ViewDataBinding

abstract class BaseBoundActivity<ViewModelType : BaseViewModel, ViewDataBindingType : ViewDataBinding> :
    BaseActivity<ViewDataBindingType>() {

    override fun preInflate() {
    }

    override fun postInflate(viewDataBinding: ViewDataBinding?) {
        viewDataBinding?.let {
            it.lifecycleOwner = this
            it.executePendingBindings()
        }
    }
}
