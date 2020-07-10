package com.syrous.ycceyearbook.data.local

import android.content.SharedPreferences
import com.syrous.ycceyearbook.data.model.User
import com.syrous.ycceyearbook.util.USER_EMAIL
import com.syrous.ycceyearbook.util.USER_ID
import com.syrous.ycceyearbook.util.USER_NAME
import com.syrous.ycceyearbook.util.USER_PHOTO_URL
import javax.inject.Inject

class UserProfile @Inject constructor(private val sharedPreferences: SharedPreferences) {
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

    private fun getLoggedInUser(): User =
        with(sharedPreferences) {
            User(
                id = getString(USER_ID, null)!!,
                name = getString(USER_NAME, null),
                email = getString(USER_EMAIL, null),
                profilePhotoUrl = getString(USER_PHOTO_URL, null)
            )
        }

    private fun logout(): Boolean =
        with(sharedPreferences.edit()) {
            remove(USER_PHOTO_URL)
            remove(USER_EMAIL)
            remove(USER_NAME)
            remove(USER_ID)
            commit()
        }
}