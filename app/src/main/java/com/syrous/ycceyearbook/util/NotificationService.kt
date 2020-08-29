package com.syrous.ycceyearbook.util

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.syrous.ycceyearbook.data.local.UserSharedPrefStorage
import timber.log.Timber

class NotificationService constructor(private val storage: UserSharedPrefStorage): FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
    }

    override fun onNewToken(token: String) {
        Timber.d("Token : $token")
        storage.saveNotificationToken(token)
    }
}