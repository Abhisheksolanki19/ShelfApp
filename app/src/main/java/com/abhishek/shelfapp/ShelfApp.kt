package com.abhishek.shelfapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ShelfApp: Application(){

    companion object {
        lateinit var INSTANCE: ShelfApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

}