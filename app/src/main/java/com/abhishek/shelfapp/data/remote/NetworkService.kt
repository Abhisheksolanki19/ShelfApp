package com.abhishek.shelfapp.data.remote

import com.abhishek.shelfapp.data.model.Book
import retrofit2.http.GET

interface NetworkService {

    @GET("test/fetch/book/list")
    suspend fun fetchBookList() : List<Book>
}