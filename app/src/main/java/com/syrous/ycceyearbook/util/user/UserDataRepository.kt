package com.syrous.ycceyearbook.util.user

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.syrous.ycceyearbook.data.model.Result
import com.syrous.ycceyearbook.data.model.User
import timber.log.Timber
import javax.inject.Inject

class UserDataRepository @Inject constructor (
    private val userManager: UserManager
) {

    suspend fun loginInUser(auth: FirebaseAuth, account: GoogleSignInAccount): Result<User> {
        Timber.d("In User Data Repository")
        return userManager.loginUser(auth, account)
   }

}