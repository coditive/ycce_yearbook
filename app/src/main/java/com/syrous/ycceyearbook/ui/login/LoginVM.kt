package com.syrous.ycceyearbook.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.syrous.ycceyearbook.util.UserDataRepository
import javax.inject.Inject


class LoginVM @Inject constructor(private val userDataRepository: UserDataRepository) : ViewModel() {

    private val _status = MutableLiveData(LoginState.NOT_LOGGED_IN)
    val status: LiveData<LoginState> = _status

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading


}


enum class LoginState {
    NOT_LOGGED_IN,
    IN_PROGRESS,
    LOGGED_IN,
    LOGIN_ERROR
}