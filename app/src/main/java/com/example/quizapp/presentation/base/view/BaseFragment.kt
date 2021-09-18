package com.example.quizapp.presentation.base.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.quizapp.R
import com.example.quizapp.core.Failure
import com.example.quizapp.core.extensions.showSnackbar
import com.example.quizapp.core.extensions.showToast

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
        if (layoutId != 0) {
            binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        }

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
