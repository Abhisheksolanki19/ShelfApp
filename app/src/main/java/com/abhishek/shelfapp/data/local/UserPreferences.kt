package com.abhishek.shelfapp.data.local

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(
    private val prefs: SharedPreferences
) {

    companion object {
        private const val USERNAME = "USERNAME"
        private const val PASSWORD = "PASSWORD"
        private const val COUNTRY = "COUNTRY"
        private const val IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN"
    }

    fun getUsername(): String? =
        prefs.getString(USERNAME, null)

    fun setUsername(username: String?) =
        prefs.edit().putString(USERNAME, username).apply()

    fun getUserPassword(): String? =
        prefs.getString(PASSWORD, null)

    fun setUserPassword(username: String?) =
        prefs.edit().putString(PASSWORD, username).apply()

    fun getUserCountry(): String? =
        prefs.getString(COUNTRY, null)

    fun setUserCountry(username: String?) =
        prefs.edit().putString(COUNTRY, username).apply()

    fun getIsUserLoggedIn(): Boolean =
        prefs.getBoolean(IS_USER_LOGGED_IN, false)

    fun setIsUserLoggedIn(isLoggedIn: Boolean) =
        prefs.edit().putBoolean(IS_USER_LOGGED_IN, isLoggedIn).apply()

    fun getIsFavourite(id: String): Boolean =
        prefs.getBoolean("item_$id", false)

    fun setIsFavourite(id: String, isFavourite: Boolean) =
        prefs.edit().putBoolean("item_$id", isFavourite).apply()
}