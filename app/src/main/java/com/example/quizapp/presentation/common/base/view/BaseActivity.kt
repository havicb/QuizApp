package com.example.quizapp.presentation.common.base.view

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

// extracted common logic for activities inside this abstract class
abstract class BaseActivity<ViewBindingType : ViewDataBinding> : AppCompatActivity() {

    @get:LayoutRes
    abstract val layoutId: Int

    protected lateinit var binding: ViewBindingType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preInflate()
        if (layoutId != 0) {
            binding = DataBindingUtil.setContentView(this, layoutId)
        }
        postInflate(binding)
    }

    // this method will be called before inflating the view
    abstract fun preInflate()

    // this method will be called after inflating the view
    abstract fun postInflate(viewDataBinding: ViewDataBinding?)
}
