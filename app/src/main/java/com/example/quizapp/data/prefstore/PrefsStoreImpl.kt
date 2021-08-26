package com.example.quizapp.data.prefstore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(PrefsStoreImpl.PREFERENCE_STORE_NAME)

// New android async library for storing small amount of data
class PrefsStoreImpl(context: Context) : PrefsStore {

    private val dataStore = context.dataStore

    override suspend fun saveAuthToken(authToken: String): Unit = with(dataStore) {
        edit { preferences ->
            preferences[PreferenceKeys.AUTH_TOKEN] = authToken
        }
    }

    override suspend fun getAuthToken(): Flow<String> = with(dataStore.data) {
        return map { preferences ->
            preferences[PreferenceKeys.AUTH_TOKEN] ?: ""
        }
    }

    override suspend fun clear(): Unit = with(dataStore) {
        edit {
            it.clear()
        }
    }

    private object PreferenceKeys {
        val AUTH_TOKEN = stringPreferencesKey("USER_AUTH_TOKEN")
    }

    companion object {
        internal const val PREFERENCE_STORE_NAME = "PREFERENCES_STORAGE"
    }
}
