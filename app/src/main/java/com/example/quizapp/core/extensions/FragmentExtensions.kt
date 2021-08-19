package com.example.quizapp.core.extensions

import android.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.quizapp.R

fun Fragment.showGenericDialog(title: String, message: String, onButtonClick: (() -> Unit)? = null) {
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
