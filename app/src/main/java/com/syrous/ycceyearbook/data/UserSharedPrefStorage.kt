package com.syrous.ycceyearbook.data

import android.content.SharedPreferences
import com.syrous.ycceyearbook.model.Result
import com.syrous.ycceyearbook.model.Result.Error
import com.syrous.ycceyearbook.model.Result.Success
import com.syrous.ycceyearbook.model.User
import com.syrous.ycceyearbook.util.USER_EMAIL
import com.syrous.ycceyearbook.util.USER_ID
import com.syrous.ycceyearbook.util.USER_NAME
import com.syrous.ycceyearbook.util.USER_PHOTO_URL
import timber.log.Timber
import javax.inject.Inject

class UserSharedPrefStorage @Inject constructor(private val sharedPreferences: SharedPreferences) {
    fun saveAccount(user: User) {
        sharedPreferences.edit().apply {
            putString(USER_EMAIL, user.email)
            putString(USER_ID, user.id)
            putString(USER_NAME, user.name)
            putString(USER_PHOTO_URL, user.profilePhotoUrl)
            apply()
        }
        Timber.d("User Saved: $user")
    }

    fun getLoggedInUser(): Result<User> =
        try {
            with(sharedPreferences) {
                val user = User(
                    id = getString(USER_ID, null)!!,
                    name = getString(USER_NAME, null),
                    email = getString(USER_EMAIL, null),
                    profilePhotoUrl = getString(USER_PHOTO_URL, null)
                )
                return@with Success(user)
            }
        } catch (e: Exception) {
            Error(e)
        }


    fun logout(): Boolean =
        with(sharedPreferences.edit()) {
            remove(USER_PHOTO_URL)
            remove(USER_EMAIL)
            remove(USER_NAME)
            remove(USER_ID)
            commit()
        }
}