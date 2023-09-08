package com.abhishek.shelfapp.data.repository

import android.app.Application
import com.abhishek.shelfapp.data.model.Book
import com.abhishek.shelfapp.data.remote.NetworkService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val networkService: NetworkService,
) {

    fun getBooksData(): Flow<List<Book>> {
        return flow {
            emit(networkService.fetchBookList())
        }
    }

}