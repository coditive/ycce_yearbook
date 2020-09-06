package com.syrous.ycceyearbook.util

import android.content.Context
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.syrous.ycceyearbook.data.local.UserSharedPrefStorage
import com.syrous.ycceyearbook.model.Result.Error
import com.syrous.ycceyearbook.model.Result.Success
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
            sendNotification(applicationContext, it.title!!,it.body!!)
        }
    }

    override fun onNewToken(token: String) {
        Timber.d("Token : $token")
        storage.saveNotificationToken(token)
        sendTokenToServer(token)
    }

    private fun sendTokenToServer(token: String) {
       val result = storage.getLoggedInUser()
        val db = FirebaseFirestore.getInstance()
        if(result is Success) {
            val user = result.data
            val data = hashMapOf(
                USER_NOTIFICATION_TOKEN to token,
                UPDATE_TIMESTAMP to listOf(FieldValue.serverTimestamp())
            )
            val query = db.collection(USER_COLLECTION).document(user.googleid)
            query.set(user)
            query.set(data)
        } else if(result is Error) {
            TODO("")
        }

    }

}