package com.syrous.ycceyearbook.data.local

import android.content.SharedPreferences
import com.syrous.ycceyearbook.model.Result
import com.syrous.ycceyearbook.model.Result.Error
import com.syrous.ycceyearbook.model.Result.Success
import com.syrous.ycceyearbook.model.User
import com.syrous.ycceyearbook.util.*
import timber.log.Timber
import javax.inject.Inject

class UserSharedPrefStorage @Inject constructor(private val sharedPreferences: SharedPreferences) {
    fun saveAccount(user: User) {
        sharedPreferences.edit().apply {
            putString(USER_EMAIL, user.email)
            putString(USER_GOOGLE_ID, user.googleid)
            putString(USER_AUTH_ID, user.authid)
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
                    googleid = getString(USER_GOOGLE_ID, null)!!,
                    authid = getString(USER_AUTH_ID, null)!!,
                    name = getString(USER_NAME, null),
                    email = getString(USER_EMAIL, null),
                    ntToken = getString(USER_NOTIFICATION_TOKEN, null),
                    profilePhotoUrl = getString(USER_PHOTO_URL, null),
                    timestamp = null
                )
                return@with Success(user)
            }
        } catch (e: Exception) {
            Error(e)
        }

    fun saveNotificationToken(token: String) {
        sharedPreferences.edit().apply {
            putString(USER_NOTIFICATION_TOKEN, token)
            apply()
        }
        Timber.d("Saved Notification Token : $token")
    }

    fun getNotificationToken(): Result<String> =
        try {
            with(sharedPreferences) {
                val token = getString(USER_NOTIFICATION_TOKEN, null) as String
                return@with Success(token)
            }
        }catch (e: Exception) {
            Error(e)
        }

    fun logout(): Boolean =
        with(sharedPreferences.edit()) {
            remove(USER_PHOTO_URL)
            remove(USER_EMAIL)
            remove(USER_NAME)
            remove(USER_AUTH_ID)
            remove(USER_NOTIFICATION_TOKEN)
            remove(USER_GOOGLE_ID)
            commit()
        }
}