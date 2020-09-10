package com.syrous.ycceyearbook.util.user

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.syrous.ycceyearbook.data.local.UserSharedPrefStorage
import com.syrous.ycceyearbook.model.Result
import com.syrous.ycceyearbook.model.Result.Error
import com.syrous.ycceyearbook.model.Result.Success
import com.syrous.ycceyearbook.model.User
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserDataRepository @Inject constructor (
    private val userManager: UserManager,
    private val storage: UserSharedPrefStorage
) {

    fun getLoggedInUser() : Result<User> {
        return storage.getLoggedInUser()
    }

    suspend fun loginAndStoreUser(account: GoogleSignInAccount): Result<User> {
        val user = userManager.loginUser(account)
        if(user is Success) {
            storage.saveAccount(user.data)
        } else if(user is Error){
            TODO("Throw error")
        }
        return user
    }

    suspend fun logoutFromFireStoreAndLocal() {
        userManager.logout()
        storage.logout()
    }
}