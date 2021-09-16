package com.example.quizapp.data

import android.content.Context
import com.example.quizapp.R

interface StorageConfig {
    val preferenceKey: String
    val authTokenKey: String
}

class StorageConfigImpl(context: Context) : StorageConfig {
    override val preferenceKey: String = context.getString(R.string.preference_key)
    override val authTokenKey: String = context.getString(R.string.auth_key)
}