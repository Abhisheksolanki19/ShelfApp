package com.abhishek.shelfapp.di

import com.abhishek.shelfapp.data.remote.NetworkService
import com.abhishek.shelfapp.data.repository.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//abstract class RepositoryModule {
//
//    @Binds
//    @Singleton
//    abstract fun bindHomeRepository(networkService: NetworkService): HomeRepository
//}