package com.example.quizapp.presentation.base.view

import android.util.Log
import androidx.databinding.ViewDataBinding
import com.example.quizapp.R
import com.example.quizapp.core.Failure
import com.example.quizapp.core.extensions.navController
import com.example.quizapp.core.extensions.showSnackbar
import com.example.quizapp.core.extensions.showToast
import com.example.quizapp.presentation.base.NavigationEvent
import com.example.quizapp.presentation.main.MainActivity

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
        initBaseObserver()
    }

    // method for UI manipulation
    open fun initUI() {}

    protected fun handleCommonNetworkErrors(failure: Failure.NetworkFailure) {
        when (failure) {
            is Failure.ServerFailure -> showToast(getString(R.string.server_error))
            is Failure.NetworkConnectionFailure -> showToast(failure.message)
            is Failure.BadRequest -> showSnackbar(failure.message)
            is Failure.Forbidden -> showToast(getString(R.string.forbidden_access_error))
            is Failure.NotAuthorized -> showToast(getString(R.string.unauthorized_error))
            is Failure.NotFound -> showToast(getString(R.string.not_found_error))
        }
    }

    private fun initBaseObserver() {
        viewModel.navigationEvent.observe(viewLifecycleOwner) {
            when (it) {
                is NavigationEvent.To -> navController.navigate(it.directions)
                is NavigationEvent.Back -> navController.navigateUp()
            }
        }
        viewModel.error.observe(viewLifecycleOwner) { failure ->
            when (failure) {
                is Failure.NetworkFailure -> handleCommonNetworkErrors(failure)
                else -> Unit
            }
        }
        viewModel.screenState.observe(this) {
            (activity as MainActivity).updateScreen(it)
        }
    }
}
