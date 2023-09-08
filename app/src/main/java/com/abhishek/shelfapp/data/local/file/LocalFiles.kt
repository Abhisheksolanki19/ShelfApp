package com.abhishek.shelfapp.data.local.file

import android.content.Context
import com.abhishek.shelfapp.R
import com.abhishek.shelfapp.data.model.Country
import com.abhishek.shelfapp.utils.common.FileUtils
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalFiles @Inject constructor(val context: Context){

    fun getCountry(): Flow<List<Country>> {
        return FileUtils.readRawFile(context, R.raw.country_list)
            .map {
                return@map Moshi.Builder()
                    .addLast(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
                    .build()
                    .adapter<List<Country>>(
                        Types.newParameterizedType(
                            List::class.java,
                            Country::class.java
                        )
                    )
                    .fromJson(it)
                    ?: return@map emptyList<Country>()
            }
    }
}