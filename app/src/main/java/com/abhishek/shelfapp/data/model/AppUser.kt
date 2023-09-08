package com.abhishek.shelfapp.data.model

/**
 * Used for the current logged in user
 */
data class AppUser(
    val userName: String,

    val userPassword: String,

    val country: String
)