package com.example.finalproject.data.sharedpreference

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class SecureStorage(context: Context) {
    private val sharedPreferences = createEncryptedSharedPreferences(context)

    companion object {
        private const val SHARED_PREFS_NAME = "secure_shared_prefs"
        private const val KEY_TOKEN = "token"
        private const val KEY_USERNAME = "username"
        private const val KEY_PASSWORD = "password"

        private fun createEncryptedSharedPreferences(context: Context) =
            EncryptedSharedPreferences.create(
                SHARED_PREFS_NAME,
                MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
    }

    var token: String?
        get() = sharedPreferences.getString(KEY_TOKEN, null)
        set(value) = sharedPreferences.edit().putString(KEY_TOKEN, value).apply()

    var username: String?
        get() = sharedPreferences.getString(KEY_USERNAME, null)
        set(value) = sharedPreferences.edit().putString(KEY_USERNAME, value).apply()

    var password: String?
        get() = sharedPreferences.getString(KEY_PASSWORD, null)
        set(value) = sharedPreferences.edit().putString(KEY_PASSWORD, value).apply()
}
