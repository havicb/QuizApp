package com.example.quizapp.presentation.main.quiz.question

import android.widget.TextView
import com.example.quizapp.R

interface Selectable {
    val textView: TextView?
    val backgroundColor: Int
    val textColor: Int
        get() = R.color.black
}
