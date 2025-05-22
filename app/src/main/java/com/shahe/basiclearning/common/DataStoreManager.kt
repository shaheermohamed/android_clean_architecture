package com.shahe.basiclearning.common

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
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

object EncryptedPrefs {
    private const val PREF_NAME = "secure_prefs"
    private lateinit var sharedPrefs: SharedPreferences

    fun init(context: Context) {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        sharedPrefs = EncryptedSharedPreferences.create(
            context,
            PREF_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveCredentials(email: String, password: String) {
        sharedPrefs.edit().apply {
            putString("EMAIL", email)
            putString("PASSWORD", password)
            apply()
        }
    }

    fun getEmail() = sharedPrefs.getString("EMAIL", null)
    fun getPassword() = sharedPrefs.getString("PASSWORD", null)
}