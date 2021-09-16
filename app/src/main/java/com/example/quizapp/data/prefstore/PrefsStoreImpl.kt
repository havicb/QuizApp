package com.example.quizapp.data.prefstore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.quizapp.BuildConfig
import com.example.quizapp.data.StorageConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(BuildConfig.PREFERENCE_STORAGE_KEY)

// New android async library for storing small amount of data
class PrefsStoreImpl(
    val context: Context,
    storageConfig: StorageConfig
) : PrefsStore {

    private val dataStore = context.dataStore
    private var authKey: Preferences.Key<String>

    init {
        authKey = getStringPreferenceKey(storageConfig.authTokenKey)
    }

    override suspend fun saveAuthToken(authToken: String) {
        saveStringValue(authKey, authToken)
    }

    override suspend fun getAuthToken(): Flow<String> {
        return getStringValue(authKey)
    }

    private fun getStringPreferenceKey(keyName: String): Preferences.Key<String> {
        return stringPreferencesKey(keyName)
    }

    private suspend fun saveStringValue(key: Preferences.Key<String>, value: String) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    private fun getStringValue(key: Preferences.Key<String>): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[key] ?: ""
        }
    }

    override suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }
}
