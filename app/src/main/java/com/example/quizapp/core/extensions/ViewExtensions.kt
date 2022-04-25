package com.example.quizapp.core.extensions

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.disable() {
    isEnabled = false
    isClickable = false
}

fun View.enable() {
    isEnabled = true
    isClickable = true
}

// Can be added for actionSearch etc.
fun EditText.onActionDone(block: () -> Boolean) {
    setOnEditorActionListener { _, actionId, _ ->

        if (actionId == EditorInfo.IME_ACTION_DONE) {
            block()
            clearFocus()
        }

        false
    }
}