package com.syrous.ycceyearbook.ui.login

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.syrous.ycceyearbook.data.LoginRepository

const val GOOGLE_SIGN_IN: Int = 9001

class LoginVM(
    private val loginRepository: LoginRepository,
    private val googleSignInClient: GoogleSignInClient
) : ViewModel() {

    private val _status = MutableLiveData(LoginState.NOT_LOGGED_IN)
    val status: LiveData<LoginState> = _status

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading


//    fun signIn() {
//        val signInIntent = googleSignInClient.signInIntent
//        startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
//    }

    fun onResultFromActivity(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode) {
            GOOGLE_SIGN_IN -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            }
        }
    }


    private fun handleSignIn (completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

        } catch (e: ApiException) {

        }
    }
}


interface ActivityNavigation {
    fun startActivityForResult (intent: Intent?, requestCode: Int)
}

enum class LoginState {
    NOT_LOGGED_IN,
    IN_PROGRESS,
    LOGGED_IN,
    LOGIN_ERROR
}