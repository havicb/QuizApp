package com.example.quizapp.data.prefstore

import kotlinx.coroutines.flow.Flow

interface PrefsStore {
    suspend fun saveAuthToken(authToken: String)
    suspend fun getAuthToken(): Flow<String>
    suspend fun clear()
}
