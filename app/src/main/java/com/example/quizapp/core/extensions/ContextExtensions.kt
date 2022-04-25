package com.example.quizapp.core.extensions

import android.content.Context
import androidx.core.content.ContextCompat

fun Context.loadColor(color: Int) = ContextCompat.getColor(this, color)