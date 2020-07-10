package com.syrous.ycceyearbook.util.user

import javax.inject.Inject


@LoggedUserScope
class UserDataRepository @Inject constructor (private val userManager: UserManager) {

}