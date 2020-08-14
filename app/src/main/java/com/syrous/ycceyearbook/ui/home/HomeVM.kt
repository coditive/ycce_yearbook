package com.syrous.ycceyearbook.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.syrous.ycceyearbook.model.Result
import com.syrous.ycceyearbook.model.Result.Success
import com.syrous.ycceyearbook.model.User
import com.syrous.ycceyearbook.util.user.UserDataRepository
import javax.inject.Inject

class HomeVM @Inject constructor(
    private val repository: UserDataRepository) : ViewModel() {

    private val _userProfile = MutableLiveData<User>()
    val userProfile: LiveData<User> = _userProfile

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getUserProfile() {
        val result = repository.getLoggedInUser()
       if(result is Success) {
          _userProfile.value = result.data
        } else if(result is Result.Error) {
            _errorMessage.value = result.exception.message
       }
    }
}