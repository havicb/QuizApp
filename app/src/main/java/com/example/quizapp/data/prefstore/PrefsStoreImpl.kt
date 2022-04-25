package com.example.quizapp.data.prefstore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.quizapp.BuildConfig
import com.example.quizapp.data.StorageConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(BuildConfig.PREFERENCE_STORAGE_KEY)

// New android async library for storing small amount of data
class PrefsStoreImpl(
    val context: Context,
    storageConfig: StorageConfig
) : PrefsStore {

    private val dataStore = context.dataStore
    private var authKey: Preferences.Key<String> = stringPreferencesKey(storageConfig.authTokenKey)
    private var pointsKey: Preferences.Key<Int> = intPreferencesKey(storageConfig.pointsKey)

    override suspend fun getUserPoints(): Flow<Int> {
        return get(pointsKey, 0)
    }

    override suspend fun getAuthToken(): Flow<String> {
        return get(authKey, "")
    }

    override suspend fun saveAuthToken(authToken: String) {
        save(authKey, authToken)
    }

    override suspend fun updateUserPoints(numberOfPoints: Int) {
        dataStore.edit { pref ->
            val currentPoints = pref[pointsKey] ?: 0
            pref[pointsKey] = numberOfPoints + currentPoints
        }
    }

    private suspend fun <T> save(key: Preferences.Key<T>, value: T) {
        // todo: Check this method
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    private fun <T> get(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return dataStore.data.map { pref ->
            pref[key] ?: defaultValue
        }
    }

    override suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }
}
