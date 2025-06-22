package com.example.offline.dataStore

import android.content.SharedPreferences
import androidx.core.content.edit

class TokenLocalService(
    private  val sharedPreferences: SharedPreferences
) {
    companion object {
        const val TOKEN_PREF = "token_pref"
        const val ACCESS_TOKEN_KEY = "access_token_key"
        const val REFRESH_TOKEN_KEY = "refresh_token_key"
    }

    fun setToken(accessToken: String, refreshToken: String) {
        sharedPreferences.edit {
            putString(ACCESS_TOKEN_KEY, accessToken)
            putString(REFRESH_TOKEN_KEY, refreshToken)
        }
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
    }

    fun getRefreshToken(): String? {
        return sharedPreferences.getString(REFRESH_TOKEN_KEY, null)
    }

    fun clearToken() {
        sharedPreferences.edit {
            remove(ACCESS_TOKEN_KEY)
            remove(REFRESH_TOKEN_KEY)
        }
    }

    fun setAccessToken(accessToken: String) {
        sharedPreferences.edit {
            putString(ACCESS_TOKEN_KEY, accessToken)
        }
    }

    fun setRefreshToken(refreshToken: String) {
        sharedPreferences.edit {
            putString(REFRESH_TOKEN_KEY, refreshToken)
        }
    }
}