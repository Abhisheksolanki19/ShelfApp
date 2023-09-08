package com.abhishek.shelfapp.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek.shelfapp.data.model.AppUser
import com.abhishek.shelfapp.data.model.Country
import com.abhishek.shelfapp.data.repository.SignUpRepository
import com.abhishek.shelfapp.data.repository.UserRepository
import com.abhishek.shelfapp.utils.common.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    companion object {
        const val TAG = "LoginViewModel"
    }

    private val _currentUser = MutableLiveData<AppUser>()

    val currentUser: LiveData<AppUser> = _currentUser

    fun getCurrentUser() {
        _currentUser.value = userRepository.getCurrentUser()
    }

    fun getIsUserLoggedIn() = userRepository.getIsUserLoggedIn()

    fun setIsUserLoggedIn(isLoggedIn: Boolean) = userRepository.setIsUserLoggedIn(isLoggedIn)
}