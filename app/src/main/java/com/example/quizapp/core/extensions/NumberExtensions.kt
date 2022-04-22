package com.example.quizapp.core.extensions

import android.content.Context
import android.util.TypedValue

/**
 * Converts Integer of Pixels to Integer of DP.
 */
fun Number.toDp(context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    )
        .toInt()
}