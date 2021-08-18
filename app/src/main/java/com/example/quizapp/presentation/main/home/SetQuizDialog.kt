package com.example.quizapp.presentation.main.home

import android.app.AlertDialog
import android.content.Context
import com.example.quizapp.R

class SetQuizDialog(
    context: Context,
    private val title: String,
    val onDifficultySelected: (selectedDifficulty: QuizDifficulty) -> Unit
) {

    private val alertDialog = AlertDialog.Builder(context)
    private var selectedDifficulty = 0

    init {
        createDialog()
    }

    private fun createDialog() {
        alertDialog.apply {
            setTitle(context.getString(R.string.start_quiz_title, title))
            setSingleChoiceItems(
                arrayOf(
                    QuizDifficulty.EASY.value,
                    QuizDifficulty.MEDIUM.value,
                    QuizDifficulty.HARD.value
                ),
                0
            ) { _, item -> selectedDifficulty = item }
            setPositiveButton(R.string.ok_button) { d, _ ->
                d.dismiss()
                onDifficultySelected(getQuizDifficulty(selectedDifficulty))
            }
            show()
        }
    }

    private fun getQuizDifficulty(item: Int): QuizDifficulty {
        return arrayOf(QuizDifficulty.EASY, QuizDifficulty.MEDIUM, QuizDifficulty.HARD)[item]
    }
}

enum class QuizDifficulty(val value: String) {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard")
}
