package com.example.quizapp.data

import android.content.Context
import com.example.quizapp.R

interface StorageConfig {
    val preferenceKey: String
    val authTokenKey: String
    val pointsKey: String
}

class StorageConfigImpl(context: Context) : StorageConfig {
    override val preferenceKey: String = context.getString(R.string.preference_key)
    override val authTokenKey: String = context.getString(R.string.auth_key)
    override val pointsKey: String = context.getString(R.string.points_key)
}