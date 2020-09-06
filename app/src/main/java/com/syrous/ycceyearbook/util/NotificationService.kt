package com.syrous.ycceyearbook.util

import android.content.Context
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.syrous.ycceyearbook.data.local.UserSharedPrefStorage
import timber.log.Timber

class NotificationService: FirebaseMessagingService() {

    private lateinit var storage: UserSharedPrefStorage


    override fun onCreate() {
        super.onCreate()
        storage = UserSharedPrefStorage(applicationContext
            .getSharedPreferences(USER_SP_KEY, Context.MODE_PRIVATE))
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.notification?.let {
            Timber.d("Notification Received : ${it.body}")
            sendNotification(applicationContext, it.body!!)
        }
    }

    override fun onNewToken(token: String) {
        Timber.d("Token : $token")
        storage.saveNotificationToken(token)
    }
}