package com.abhishek.shelfapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek.shelfapp.data.model.AppUser
import com.abhishek.shelfapp.data.model.Book
import com.abhishek.shelfapp.data.repository.HomeRepository
import com.abhishek.shelfapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val homeRepository: HomeRepository
) : ViewModel() {

    init {
        fetchBooksData()
    }

    companion object {
        const val TAG = "HomeViewModel"
    }

    private val _currentUser = MutableLiveData<AppUser>()
    private val _booksList = MutableLiveData<List<Book>>()

    val currentUser: LiveData<AppUser> = _currentUser
    val booksList: LiveData<List<Book>> = _booksList

    fun getCurrentUser() {
        _currentUser.value = userRepository.getCurrentUser()
    }

    fun getIsFavourite(id: String) = userRepository.getIsFavourite(id)

    fun setIsFavourite(id: String, isFavourite: Boolean) =
        userRepository.setIsFavourite(id, isFavourite)

    fun getIsUserLoggedIn() = userRepository.getIsUserLoggedIn()

    fun setIsUserLoggedIn(isLoggedIn: Boolean) = userRepository.setIsUserLoggedIn(isLoggedIn)

    private fun fetchBooksData() {
        viewModelScope.launch {
            homeRepository.getBooksData()
                .catch { error ->
                    Log.e(TAG, error.toString())
                }
                .collect {
                    _booksList.postValue(it)
                }
        }
    }
}