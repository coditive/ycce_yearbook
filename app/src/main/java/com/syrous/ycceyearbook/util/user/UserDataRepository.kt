package com.syrous.ycceyearbook.util.user

import com.syrous.ycceyearbook.data.local.UserSharedPrefStorage
import com.syrous.ycceyearbook.model.Result
import com.syrous.ycceyearbook.model.User
import javax.inject.Inject


@LoggedUserScope
class UserDataRepository @Inject constructor (
    private val userManager: UserManager,
    private val storage: UserSharedPrefStorage
) {

    fun getLoggedInUser() : Result<User> {
        return storage.getLoggedInUser()
    }



}