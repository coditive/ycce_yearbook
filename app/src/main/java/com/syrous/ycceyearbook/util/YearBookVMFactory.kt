package com.syrous.ycceyearbook.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.syrous.ycceyearbook.data.LoginRepository
import com.syrous.ycceyearbook.ui.login.LoginVM

@Suppress("UNCHECKED_CAST")
class YearBookVMFactory(
    private val loginRepository: LoginRepository,
    private val googleSignInClient: GoogleSignInClient
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(LoginVM::class.java) -> LoginVM(loginRepository, googleSignInClient) as T
                else -> throw IllegalArgumentException("Unknown View Model class ${modelClass.name}")
            }
        }
    }
}