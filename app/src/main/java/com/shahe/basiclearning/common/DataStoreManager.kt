package com.shahe.basiclearning.common

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_prefs")

object DataStoreKeys {
    val IS_BIOMETRIC_ENABLED = booleanPreferencesKey("biometric_enabled")
}

class DataStoreManager(private val context: Context) {

    val isBiometricEnabled: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[DataStoreKeys.IS_BIOMETRIC_ENABLED] ?: false }

    suspend fun setBiometricEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DataStoreKeys.IS_BIOMETRIC_ENABLED] = enabled
        }
    }
}