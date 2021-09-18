package com.example.quizapp.core.extensions

import android.view.View
import android.widget.ViewFlipper

fun ViewFlipper.showScreen(view: View) {
    displayedChild = indexOfChild(view)
}