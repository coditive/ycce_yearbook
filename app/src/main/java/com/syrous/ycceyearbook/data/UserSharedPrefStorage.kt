package com.syrous.ycceyearbook.data

import android.app.Application
import android.content.Context
import com.syrous.ycceyearbook.data.model.User
import com.syrous.ycceyearbook.util.*
import javax.inject.Inject

class UserSharedPrefStorage @Inject constructor(context: Application) {

    private val sharedPreferences = context.getSharedPreferences(USER_SP_KEY, Context.MODE_PRIVATE)

    fun saveAccount(user: User) {
        sharedPreferences.edit().apply {
            putString(USER_EMAIL, user.email)
            putString(USER_ID, user.id)
            putString(USER_NAME, user.name)
            putString(USER_PHOTO_URL, user.profilePhotoUrl)
            apply()
        }
    }

    fun isUserLoggedIn(): Boolean = !sharedPreferences.getString(USER_ID, null).isNullOrBlank()

    fun getLoggedInUser(): User =
        with(sharedPreferences) {
            User(
                id = getString(USER_ID, null)!!,
                name = getString(USER_NAME, null),
                email = getString(USER_EMAIL, null),
                profilePhotoUrl = getString(USER_PHOTO_URL, null)
            )
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