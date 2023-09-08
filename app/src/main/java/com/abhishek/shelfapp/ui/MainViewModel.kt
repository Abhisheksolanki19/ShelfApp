package com.abhishek.shelfapp.ui

import androidx.lifecycle.ViewModel
import com.abhishek.shelfapp.data.repository.UserRepository
import com.abhishek.shelfapp.domain.repository.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun getIsUserLoggedIn() = userRepository.getIsUserLoggedIn()
}