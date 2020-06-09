package com.syrous.ycceyearbook.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.syrous.ycceyearbook.data.model.Result.Error
import com.syrous.ycceyearbook.data.model.Result.Success
import com.syrous.ycceyearbook.util.UserDataRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


class LoginVM @Inject constructor(private val userDataRepository: UserDataRepository) : ViewModel() {

    private val _status = MutableLiveData(LoginState.NOT_LOGGED_IN)
    val status: LiveData<LoginState> = _status

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun loginInUser(auth: FirebaseAuth, account: GoogleSignInAccount) {
        _loading.value = true

        viewModelScope.launch {
        val result = userDataRepository.loginInUser(auth, account)
            if(result is Success) {
                _status.value = LoginState.LOGGED_IN
            } else if(result is Error){
                Timber.d(result.exception)
                _status.value = LoginState.LOGIN_ERROR
            }
        }
    }


}


enum class LoginState {
    NOT_LOGGED_IN,
    LOGGED_IN,
    LOGIN_ERROR
}