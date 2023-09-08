package com.abhishek.shelfapp.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.abhishek.shelfapp.data.local.file.LocalFiles
import com.abhishek.shelfapp.data.remote.NetworkService
import com.abhishek.shelfapp.data.remote.Networking
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNetworkService(): NetworkService {
        return Networking.create(
            "https://test.com",
        )
    }

    @Provides
    @Singleton
    fun provideLocalFiles(application: Application): LocalFiles {
        return LocalFiles(application)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(application: Application): SharedPreferences =
        application.getSharedPreferences("shelfApp-prefs", Context.MODE_PRIVATE)
}