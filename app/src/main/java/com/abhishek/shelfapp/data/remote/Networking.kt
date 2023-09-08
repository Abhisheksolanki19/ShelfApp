package com.abhishek.shelfapp.data.remote

import com.abhishek.shelfapp.BuildConfig
import com.abhishek.shelfapp.ShelfApp
import com.abhishek.shelfapp.utils.network.FakeAPIInterceptor
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Networking {

    fun create(
        baseUrl: String,
    ): NetworkService = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(
            OkHttpClient.Builder()
                .apply {
                    if (BuildConfig.DEBUG) {
                        addInterceptor(FakeAPIInterceptor(ShelfApp.INSTANCE))
                    }
                }
                .build()
        )
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder().addLast(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory()).build()
            )
        )
        .build()
        .create(NetworkService::class.java)
}