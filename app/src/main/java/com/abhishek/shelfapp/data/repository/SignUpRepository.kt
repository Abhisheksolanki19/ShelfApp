package com.abhishek.shelfapp.data.repository

import com.abhishek.shelfapp.data.local.file.LocalFiles
import com.abhishek.shelfapp.data.model.Country
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpRepository @Inject constructor(private val localFiles: LocalFiles) {

    fun getCountryList(): Flow<List<Country>> = localFiles.getCountry()
}