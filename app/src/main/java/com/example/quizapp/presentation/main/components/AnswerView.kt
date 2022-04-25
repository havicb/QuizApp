package com.example.quizapp.presentation.main.components

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView
import androidx.core.view.setPadding
import com.example.quizapp.R
import com.example.quizapp.core.extensions.loadColor
import com.example.quizapp.core.extensions.toDp

class AnswerView(
    context: Context,
    attrs: AttributeSet,
) : TextView(context, attrs) {

    var state: State = State.DEFAULT
        set(value) {
            onStateChanged(value)
            field = value
        }

    init {
        gravity = Gravity.CENTER
        onDefault()
        setPadding(16.toDp(context))
    }

    private fun onStateChanged(newValue: State) {
        updateValue(newValue)
    }

    private fun updateValue(value: State) {
        when (value) {
            State.SELECTED -> onSelectedAnswer()
            State.WRONG_ANSWER -> onWrongAnswer()
            State.TRUE_ANSWER -> onTrueAnswer()
            State.DEFAULT -> onDefault()
        }
    }


    private fun onDefault() {
        setBackgroundResource(R.color.white)
        setTextColor(context.loadColor(R.color.cardview_dark_background))
        textSize = 12f
        setTypeface(null, Typeface.BOLD)
    }

    private fun onTrueAnswer() {
        setBackgroundResource(R.color.lightGreen)
        setTextColor(context.loadColor(R.color.white))
        setTypeface(null, Typeface.BOLD_ITALIC)
        textSize = 13f
    }

    private fun onWrongAnswer() {
        setBackgroundResource(R.color.red)
        setTextColor(context.loadColor(R.color.white))
    }

    private fun onSelectedAnswer() {
        setBackgroundResource(R.color.yellow)
        setTextColor(context.loadColor(R.color.black))
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text.toString().uppercase(), type)
    }

    enum class State {
        SELECTED, WRONG_ANSWER, TRUE_ANSWER, DEFAULT
    }
}