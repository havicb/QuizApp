package com.example.quizapp.presentation.common.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

// extracted common logic for fragments inside this fragment base class
abstract class BaseFragment<ViewBindingType : ViewDataBinding> : Fragment() {

    @get:LayoutRes
    abstract val layoutId: Int
    protected lateinit var binding: ViewBindingType

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        preInflate()
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postInflate(binding)
    }

    // this method will be called before inflating the view
    abstract fun preInflate()

    // this method will be called after inflating the view
    abstract fun postInflate(viewBindingType: ViewDataBinding?)
}
