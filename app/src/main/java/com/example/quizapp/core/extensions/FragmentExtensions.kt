package com.example.quizapp.core.extensions

import android.app.AlertDialog
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.quizapp.R
import com.google.android.material.snackbar.Snackbar

fun Fragment.showGenericDialog(
    title: String,
    message: String,
    onButtonClick: (() -> Unit)? = null
) {
    AlertDialog.Builder(requireContext()).apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton(getString(R.string.ok_button)) { d, _ ->
            if (onButtonClick == null)
                d.cancel()
            else
                onButtonClick.invoke()
        }
    }.show()
}

fun Fragment.getColor(color: Int): Int {
    return ContextCompat.getColor(requireContext(), color)
}

fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showSnackbar(message: String) {
    Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
}

val Fragment.navController: NavController
    get() = NavHostFragment.findNavController(this)
