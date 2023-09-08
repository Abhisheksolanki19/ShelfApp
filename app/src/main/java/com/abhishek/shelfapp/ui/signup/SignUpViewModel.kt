package com.abhishek.shelfapp.ui.signup

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
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val signUpRepository: SignUpRepository
) : ViewModel() {

    companion object {
        const val TAG = "SignUpViewModel"
    }

    private val _currentUser = MutableLiveData<Boolean>()
    private val _countriesData: MutableLiveData<Event<List<Country>>> = MutableLiveData()

    val currentUser: LiveData<Boolean> = _currentUser
    val countriesData: LiveData<Event<List<Country>>> = _countriesData

    fun saveCurrentUser(user: AppUser) {
        userRepository.saveCurrentUser(user)
    }

    fun getCurrentUser() {
        _currentUser.value = userRepository.getCurrentUser() != null
    }

    fun getCountryList() {
        viewModelScope.launch {
            signUpRepository.getCountryList()
                .flowOn(Dispatchers.Default)
                .catch {
                   Log.e(TAG,it.message.toString())
                }
                .collect {
                    _countriesData.postValue(Event(it))
                }
        }
    }
}