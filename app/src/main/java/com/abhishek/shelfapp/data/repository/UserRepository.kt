package com.abhishek.shelfapp.data.repository

import com.abhishek.shelfapp.data.local.UserPreferences
import com.abhishek.shelfapp.data.model.AppUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userPreferences: UserPreferences
) {

    fun setIsFavourite(id: String, isFavourite: Boolean) =
        userPreferences.setIsFavourite(id, isFavourite)

    fun getIsFavourite(id: String) = userPreferences.getIsFavourite(id)

    fun getIsUserLoggedIn() = userPreferences.getIsUserLoggedIn()

    fun setIsUserLoggedIn(isLoggedIn: Boolean) = userPreferences.setIsUserLoggedIn(isLoggedIn)

    fun saveCurrentUser(user: AppUser) {
        userPreferences.setUsername(user.userName)
        userPreferences.setUserPassword(user.userPassword)
        userPreferences.setUserCountry(user.country)
    }

    fun getCurrentUser(): AppUser? {
        val userName = userPreferences.getUsername()
        val password = userPreferences.getUserPassword()
        val country = userPreferences.getUserCountry()
        if (userName != null && password != null && country != null) {
            return AppUser(
                userName = userName,
                userPassword = password,
                country = country
            )
        }
        return null
    }
}