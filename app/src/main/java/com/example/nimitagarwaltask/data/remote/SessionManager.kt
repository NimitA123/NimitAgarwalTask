package com.example.nimitagarwaltask.data.remote

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SessionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "session_prefs"
        private const val KEY_AUTH_TOKEN = "auth_token"
    }

    fun saveToken(token: String) {
        prefs.edit() { putString(KEY_AUTH_TOKEN, token) }
    }

    fun getToken(): String? {
        return prefs.getString(KEY_AUTH_TOKEN, null)
    }

    fun clearSession() {
        prefs.edit() { clear() }
    }
}