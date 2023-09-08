package com.abhishek.shelfapp.ui.bookdetail

import androidx.lifecycle.ViewModel
import com.abhishek.shelfapp.data.repository.HomeRepository
import com.abhishek.shelfapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val homeRepository: HomeRepository
) : ViewModel() {

    companion object {
        const val TAG = "BookDetailViewModel"
    }

    fun getIsFavourite(id: String) = userRepository.getIsFavourite(id)

    fun setIsFavourite(id: String, isFavourite: Boolean) =
        userRepository.setIsFavourite(id, isFavourite)
}