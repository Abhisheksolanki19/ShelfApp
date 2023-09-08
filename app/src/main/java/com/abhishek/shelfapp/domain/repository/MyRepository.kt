package com.abhishek.shelfapp.domain.repository

interface MyRepository {

    suspend fun doNetworkCall()
}