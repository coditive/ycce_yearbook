package com.syrous.ycceyearbook.util.user

import com.syrous.ycceyearbook.data.UserSharedPrefStorage
import com.syrous.ycceyearbook.data.model.Result
import com.syrous.ycceyearbook.data.local.UserProfile
import com.syrous.ycceyearbook.data.model.Result.Success
import com.syrous.ycceyearbook.data.model.User
import javax.inject.Inject


@LoggedUserScope
class UserDataRepository @Inject constructor (private val userManager: UserManager,
                                              private val storage: UserSharedPrefStorage) {

    fun getLoggedInUser() : Result<User> {
        return Success(storage.getLoggedInUser())
    }



}